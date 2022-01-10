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

    ObjectMapper objectMapper;
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.objectMapper = new ObjectMapper();
        this.userRepository = userRepository;
    }

    @Override
    public Response handleRequest(Request request) throws JsonProcessingException {
        if(request.getMethod().equals("POST")){
            String route = request.getRoute();

            if(route.equals("/users")){
                return registration(request.getContent());

            }
            else if(route.equals("/sessions")){
                return login(request.getContent());
            }
        }
        return response(
                HttpStatus.NOT_FOUND,
                ContentType.HTML, HttpStatus.NOT_FOUND.message
        );
    }

    private Response registration(String json) throws JsonProcessingException {
        User user = objectMapper.readValue(json, User.class);
        String token = "Basic " + user.getUsername() + "-mtcgToken";
        user.setToken(token);
        user = userRepository.registration(user);
        if(user != null){
            return response(HttpStatus.OK, ContentType.JSON, user.getToken());
        }
        return response(HttpStatus.BAD_REQUEST, ContentType.HTML, HttpStatus.BAD_REQUEST.message);
    }

    private Response login(String json) throws JsonProcessingException {
        User user = objectMapper.readValue(json, User.class);
        if(userRepository.login(user) != null){
            UserService.addUser(user);
            return response(HttpStatus.OK, ContentType.HTML, HttpStatus.OK.message);
        }
        return response(HttpStatus.BAD_REQUEST, ContentType.HTML, HttpStatus.BAD_REQUEST.message);
    }


}
