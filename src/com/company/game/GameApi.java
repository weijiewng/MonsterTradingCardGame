package com.company.game;

import com.company.game.controller.BattleController;
import com.company.game.controller.UserController;
import com.company.game.repository.UserRepository;
import com.company.game.service.UserService;
import com.company.server.Request;
import com.company.server.Response;
import com.company.server.ServerApplication;
import com.fasterxml.jackson.core.JsonProcessingException;

public class GameApi implements ServerApplication {

    private UserController userController;

    public GameApi() {
        this.userController = new UserController(new UserService(), new UserRepository());
    }

    @Override
    public Response handleRequest(Request request) throws JsonProcessingException {
        return userController.handleRequest(request);
    }
}
