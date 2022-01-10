package com.company.game.controller;

import com.company.game.model.Booster;
import com.company.game.model.User;
import com.company.game.repository.TransactionRepository;
import com.company.game.repository.UserRepository;
import com.company.game.service.UserService;
import com.company.server.Request;
import com.company.server.Response;
import com.company.server.http.ContentType;
import com.company.server.http.HttpStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TransactionController extends Controller{

    ObjectMapper objectMapper;
    TransactionRepository transactionRepository;

    public TransactionController(TransactionRepository transactionRepository) {
        objectMapper = new ObjectMapper();
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Response handleRequest(Request request) throws JsonProcessingException {
        if(UserService.checkIfUserIsLoggedIn(request.getAuthorization())) {
            return buyPackage(request.getContent(), request.getAuthorization());
        }
        return response(
                HttpStatus.NOT_FOUND,
                ContentType.HTML, HttpStatus.NOT_FOUND.message
        );
    }

    private Response buyPackage(String json, String token) throws JsonProcessingException {
        JsonNode jsonNode = objectMapper.readTree(json);
        String boosterName = jsonNode.get("Booster Name").asText();

        Booster booster = transactionRepository.getBooster(boosterName);
        User user = UserService.getUser(token);
        if(booster != null){
            if(user.getCoins() >= booster.getBoosterName().cost){
                for (int i = 0; i < booster.getBooster().size(); i++) {
                    if(!transactionRepository.addCardToUser(user.getToken(), booster.getBooster().get(i).getId())){
                        return response(HttpStatus.BAD_REQUEST, ContentType.HTML, HttpStatus.BAD_REQUEST.message);
                    }
                }
                transactionRepository.removeBooster(booster.getId());
                user.setCoins(user.getCoins() - booster.getBoosterName().cost);
                transactionRepository.setCoins(user.getCoins(), user.getToken());
                return response(HttpStatus.OK, ContentType.HTML, "Acquired " + booster.getBoosterName().name);
            }
            else{
                return response(HttpStatus.BAD_REQUEST, ContentType.HTML, "No Coins");
            }
        }
        else{
            return response(HttpStatus.BAD_REQUEST, ContentType.HTML, "No Booster");
        }
    }
}
