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
        String route = request.getRoute();
        if(request.getMethod().equals("POST")){
            if(route.equals("/users")){
                return registration(request.getContent());
            }
            else if(route.equals("/sessions")){
                return login(request.getContent());
            }
        }
        else if(request.getMethod().equals("GET")){
            if(UserService.checkIfUserIsLoggedIn(request.getAuthorization())){
                if(route.equals("/stats")){
                    return getStats(request.getAuthorization());
                }
                else if (route.equals("/score")){
                    return getScore(request.getAuthorization());
                }
                String[] split = route.split("/");
                String name = split[split.length - 1];
                if(name.equals(UserService.getUser(request.getAuthorization()).getUsername())){
                    return getUserData(request.getAuthorization());
                }
            }
        }
        else if(request.getMethod().equals("PUT")){
            if(UserService.checkIfUserIsLoggedIn(request.getAuthorization())){
                String[] split = route.split("/");
                String name = split[split.length - 1];
                String username = UserService.getUser(request.getAuthorization()).getUsername();
                if(name.equals(UserService.getUser(request.getAuthorization()).getUsername())){
                    return setUserData(request.getAuthorization(), request.getContent());
                }
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

    private Response getUserData(String token){
        User user = userRepository.getUserData(token);
        if(user != null){
            return response(HttpStatus.OK, ContentType.JSON, user.jsonUserData());
        }
        return response(HttpStatus.BAD_REQUEST, ContentType.HTML, HttpStatus.BAD_REQUEST.message);
    }

    private Response getStats(String token){
        User user = userRepository.getStats(token);
        if(user != null){
            return response(HttpStatus.OK, ContentType.JSON, user.jsonStats());
        }
        return response(HttpStatus.BAD_REQUEST, ContentType.HTML, HttpStatus.BAD_REQUEST.message);
    }

    private Response getScore(String token){
        User user = userRepository.getScore(token);
        if(user != null){
            return response(HttpStatus.OK, ContentType.JSON, user.jsonScore());
        }
        return response(HttpStatus.BAD_REQUEST, ContentType.HTML, HttpStatus.BAD_REQUEST.message);
    }

    private Response setUserData(String token, String json) throws JsonProcessingException {
        User user = objectMapper.readValue(json, User.class);
        user.setToken(token);
        if(userRepository.setUserData(user.getToken(), user.getName(), user.getBio(), user.getImage())){
            return response(HttpStatus.OK, ContentType.HTML, HttpStatus.OK.message);
        }
        return response(HttpStatus.BAD_REQUEST, ContentType.HTML, HttpStatus.BAD_REQUEST.message);
    }


}
