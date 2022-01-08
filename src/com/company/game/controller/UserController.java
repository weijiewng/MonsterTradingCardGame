package com.company.game.controller;

import com.company.game.model.User;
import com.company.game.repository.UserRepository;
import com.company.game.service.UserService;
import com.company.server.Request;
import com.company.server.Response;
import com.company.server.http.ContentType;
import com.company.server.http.HttpStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UserController extends Controller{

    private final UserService userService;
    private final UserRepository userRepository;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @Override
    public Response handleRequest(Request request) throws JsonProcessingException {
        if(request.getMethod().equals("POST")){
            String[] splits = request.getRoute().split("/");
            String route = splits[splits.length - 1];
            if(route.equals("users")){
                return registration(request.getContent());
            }
            else if(route.equals("sessions")){
                return login(request.getContent());
            }
        }
        return response(
                HttpStatus.NOT_FOUND,
                ContentType.JSON,
                "{ \"error\": \"Not Found\"}"
        );
    }

    private Response registration(String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        User user = objectMapper.readValue(json, User.class);
        String token = "Basic " + user.getUsername() + "-mtcgToken";
        user.setToken(token);
        userRepository.registration(user);
        return json(user);
    }

    private Response login(String json){
        User user = toObject(json, User.class);
        userRepository.login(user);
        return json(user);
    }
}
