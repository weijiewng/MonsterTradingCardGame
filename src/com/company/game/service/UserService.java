package com.company.game.service;

import com.company.game.model.User;

import java.util.ArrayList;

public class UserService {
    static ArrayList<User> loggedInUser = new ArrayList<User>();

    public UserService() {;
    }

    public static void addUser(User user){
        loggedInUser.add(user);
    }

    public static User getUser(String token){
        for (int i = 0; i < loggedInUser.size(); i++) {
            if(loggedInUser.get(i).getToken().equals(token)){
                return loggedInUser.get(i);
            }
        }
        return null;
    }

    public static boolean checkIfUserIsLoggedIn(String token){
        for (int i = 0; i < loggedInUser.size(); i++) {
            if(loggedInUser.get(i).getToken().equals(token)){
                return true;
            }
        }
        return false;
    }
}
