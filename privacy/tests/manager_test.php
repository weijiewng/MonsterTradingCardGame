<?php
// This file is part of Moodle - http://moodle.org/
//
// Moodle is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// Moodle is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with Moodle.  If not, see <http://www.gnu.org/licenses/>.

/**
 * Privacy manager unit tests.
 *
 * @package     core_privacy
 * @copyright   2018 Jake Dallimore <jrhdallimore@gmail.com>
 * @license     http://www.gnu.org/copyleft/gpl.html GNU GPL v3 or later
 */

defined('MOODLE_INTERNAL') || die();
global $CFG;
require_once($CFG->dirroot . '/privacy/tests/fixtures/mock_null_provider.php');
require_once($CFG->dirroot . '/privacy/tests/fixtures/mock_provider.php');
require_once($CFG->dirroot . '/privacy/tests/fixtures/mock_plugin_subplugin_provider.php');
require_once($CFG->dirroot . '/privacy/tests/fixtures/mock_mod_with_user_data_provider.php');

use \core_privacy\local\request\writer;

/**
 * Privacy manager unit tests.
 *
 * @copyright   2018 Jake Dallimore <jrhdallimore@gmail.com>
 * @license     http://www.gnu.org/copyleft/gpl.html GNU GPL v3 or later
 */
class privacy_manager_testcase extends advanced_testcase {
    /**
     * Test tearDown.
     */
    public function tearDown() {
        \core_privacy\local\request\writer::reset();
    }

    /**
     * Helper to spoof the results of the internal function get_components_list, allowing mock components to be tested.
     *
     * @param array $componentnames and array of component names to include as valid core components.
     * @return PHPUnit_Framework_MockObject_MockObject
     */
    protected function get_mock_manager_with_core_components($componentnames) {
        $mock = $this->getMockBuilder(\core_privacy\manager::class)
            ->setMethods(['get_component_list'])
            ->getMock();
        $mock->expects($this->any())
            ->method('get_component_list')
            ->will($this->returnValue($componentnames));
        return $mock;
    }

    /**
     * Test collection of metadata for components implementing a metadata provider.
     */
    public function test_get_metadata_for_components() {
        // Get a mock manager, in which the core components list is mocked to include all mock plugins.
        // testcomponent is a core provider, testcomponent2 is a null provider, testcomponent3 is subplugin provider (non core).
        $mockman = $this->get_mock_manager_with_core_components(['mod_testcomponent', 'mod_testcomponent2', 'mod_testcomponent3']);

        // Core providers and shared providers both implement the metadata provider.
        $collectionarray = $mockman->get_metadata_for_components();
        $this->assertArrayHasKey('mod_testcomponent', $collectionarray);
        $collection = $collectionarray['mod_testcomponent'];
        $this->assertInstanceOf(\core_privacy\local\metadata\collection::class, $collection);
        $this->assertArrayHasKey('mod_testcomponent3', $collectionarray);
        $collection = $collectionarray['mod_testcomponent3'];
        $this->assertInstanceOf(\core_privacy\local\metadata\collection::class, $collection);

        // Component which implements just the local\metadata\null_provider. Metadata is not provided.
        $this->assertArrayNotHasKey('mod_testcomponent2', $collectionarray);
    }

    /**
     * Test that get_contexts_for_userid() only returns contextlist collections for core providers.
     */
    public function test_get_contexts_for_userid() {
        // Get a mock manager, in which the core components list is mocked to include all mock plugins.
        // testcomponent is a core provider, testcomponent2 is a null provider, testcomponent3 is subplugin provider (non core).
        $mockman = $this->get_mock_manager_with_core_components(['mod_testcomponent', 'mod_testcomponent2', 'mod_testcomponent3']);

        // Get the contextlist_collection.
        $contextlistcollection = $mockman->get_contexts_for_userid(10);
        $this->assertInstanceOf(\core_privacy\local\request\contextlist_collection::class, $contextlistcollection);

        ob_flush();

        // Verify we have a contextlist for the component in the collection.
        $this->assertInstanceOf(\core_privacy\local\request\contextlist::class,
                                $contextlistcollection->get_contextlist_for_component('mod_testcomponent'));

        // Verify we don't have a contextlist for the shared provider in the collection.
        $this->assertNull($contextlistcollection->get_contextlist_for_component('mod_testcomponent3'));

        // Verify we don't have a contextlist for the component which does not store user data.
        $this->assertEmpty($contextlistcollection->get_contextlist_for_component('mod_testcomponent2'));
    }

    /**
     * Test verifying the output of component_is_compliant.
     */
    public function test_component_is_compliant() {
        // Get a mock manager, in which the core components list is mocked to include all mock plugins.
        // testcomponent is a core provider, testcomponent2 is a null provider, testcomponent3 is subplugin provider (non core).
        $mockman = $this->get_mock_manager_with_core_components(['mod_testcomponent', 'mod_testcomponent2', 'mod_testcomponent3']);

        // A core_provider plugin implementing all required interfaces (local\metadata\provider, local\request\plugin_provider).
        $this->assertTrue($mockman->component_is_compliant('mod_testcomponent'));

        // A component implementing just the \core_privacy\local\metadata\null_provider is compliant.
        $this->assertTrue($mockman->component_is_compliant('mod_testcomponent2'));

        // A shared provider plugin implementing all required interfaces (local\metadata\provider, local\request\plugin\subplugin_provider)
        // is compliant.
        $this->assertTrue($mockman->component_is_compliant('mod_testcomponent3'));

        // A component implementing none of the providers.
        $this->assertFalse($mockman->component_is_compliant('tool_thisisnotarealtool123'));
    }

    /**
     *  Test verifying only approved contextlists can be used with the export_user_data method.
     */
    public function test_export_user_data() {
        // Get a mock manager, in which the core components list is mocked to include all mock plugins.
        // testcomponent is a core provider, testcomponent2 is a null provider, testcomponent3 is subplugin provider (non core).
        $mockman = $this->get_mock_manager_with_core_components(['mod_testcomponent', 'mod_testcomponent2', 'mod_testcomponent3', 'mod_testcomponent4']);

        // Get the non-approved contextlists.
        $contextlistcollection = $mockman->get_contexts_for_userid(10);

        // Create an approved contextlist.
        $approvedcontextlistcollection = new \core_privacy\local\request\contextlist_collection(10);
        foreach ($contextlistcollection->get_contextlists() as $contextlist) {
            $approvedcontextlist = new \core_privacy\local\request\approved_contextlist(new stdClass(), $contextlist->get_component(),
                $contextlist->get_contextids());
            $approvedcontextlistcollection->add_contextlist($approvedcontextlist);
        }
        // Verify the mocked return from the writer, meaning the manager method exited normally.
        $this->assertEquals('mock_path', $mockman->export_user_data($approvedcontextlistcollection));

        // Verify that a user preference was exported for 'mod_testcomponent4'.
        $prefs = writer::with_context(\context_system::instance())->get_user_preferences('mod_testcomponent4');
        $this->assertNotEmpty($prefs);
        $this->assertNotEmpty($prefs->mykey);
        $this->assertEquals('myvalue', $prefs->mykey->value);
        $this->assertEquals('mydescription', $prefs->mykey->description);

        // Verify an exception is thrown if trying to pass in a collection of non-approved_contextlist items.
        $this->expectException(moodle_exception::class);
        $mockman->export_user_data($contextlistcollection);
    }

    /**
     *  Test verifying only approved contextlists can be used with the delete_user_data method.
     */
    public function test_delete_user_data() {
        $this->resetAfterTest();
        // Get a mock manager, in which the core components list is mocked to include all mock plugins.
        // testcomponent is a core provider, testcomponent2 is a null provider, testcomponent3 is subplugin provider (non core).
        $mockman = $this->get_mock_manager_with_core_components(['mod_testcomponent', 'mod_testcomponent2', 'mod_testcomponent3']);

        // Get the non-approved contextlists.
        $user = \core_user::get_user_by_username('admin');
        $contextlistcollection = $mockman->get_contexts_for_userid($user->id);

        // Create an approved contextlist.
        $approvedcontextlistcollection = new \core_privacy\local\request\contextlist_collection($user->id);
        foreach ($contextlistcollection->get_contextlists() as $contextlist) {
            $approvedcontextlist = new \core_privacy\local\request\approved_contextlist($user, $contextlist->get_component(),
                $contextlist->get_contextids());
            $approvedcontextlistcollection->add_contextlist($approvedcontextlist);
        }

        // Verify null, as the method has no return type and exits normally. Mainly checking we don't see any exception.
        $this->assertNull($mockman->delete_user_data($approvedcontextlistcollection));

        // Verify an exception is thrown if trying to pass in a collection of non-approved_contextlist items.
        $this->expectException(moodle_exception::class);
        $mockman->delete_user_data($contextlistcollection);
    }
}