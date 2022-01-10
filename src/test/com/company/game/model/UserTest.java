package com.company.game.model;

import junit.framework.TestCase;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserTest extends TestCase {
    User user;

    @BeforeEach
    public void setUp(){
        user = new User();
        user.setToken("token");
        user.setUsername("testusername");
        user.setPassword("testpassword");
        user.setWin(12);
        user.setLose(10);
        user.setDraw(13);
        user.setName("testname");
        user.setBio("testbio");
        user.setImage("testimage");
    }

    @Test
    public void testJsonUserData() {
        assertEquals(user.jsonUserData(), "{\"username\" = \"testusername\", \"coins\" = 20, \"name\" = \"testname\"," +
                " \"bio\" = \"testbio\", \"image\" = \"testimage\"}");
    }

    @Test
    public void testJsonStats() {
        assertEquals(user.jsonStats(), "{\"username\" = \"testusername\", \"win\" = 12," +
                " \"lose\" = 10, \"draw\" = 13}");
    }

    @Test
    public void testJsonScore() {
        assertEquals(user.jsonScore(), "{\"username\" = \"testusername\", \"elo\" = 100, \"win\" = 12," +
                " \"lose\" = 10, \"draw\" = 13}");
    }
}