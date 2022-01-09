package com.company.game.service;

import com.company.game.model.User;

import java.util.ArrayList;

public class UserService {
    ArrayList<User> userList;

    public UserService() {
        this.userList = new ArrayList<User>();
    }

    public void addUser(User user){
        userList.add(user);
    }
}
