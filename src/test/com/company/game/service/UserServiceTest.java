package com.company.game.service;

import com.company.game.model.User;
import junit.framework.TestCase;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class UserServiceTest extends TestCase {

    User user1;
    User user2;

    @BeforeEach
    public void setUp(){
        user1 = new User();
        user2 = new User();
        user1.setToken("token1");
        user2.setToken("token2");
        UserService.addUser(user1);
        UserService.addUser(user2);
    }

    @Test
    public void testGetUser() {
        String token = "token1";
        User testUser = UserService.getUser(token);
        assertEquals(testUser.getToken(), user1.getToken());
    }

    @Test
    public void testCheckIfUserIsLoggedIn() {
        String token = "token1";
        assertTrue(UserService.checkIfUserIsLoggedIn(token));
    }

    public void testCheckIfUserIsNotLoggedIn(){
        String token = "token3";
        assertFalse(UserService.checkIfUserIsLoggedIn(token));
    }
}