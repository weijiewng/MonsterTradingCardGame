package com.company.game.controller;

import com.company.game.model.Card;
import com.company.game.repository.DeckRepository;
import com.company.game.repository.UserRepository;
import com.company.game.service.DeckService;
import com.company.game.service.UserService;
import com.company.server.Request;
import com.company.server.Response;
import com.company.server.http.ContentType;
import com.company.server.http.HttpStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.LinkedList;


public class DeckController extends Controller{

    ObjectMapper objectMapper;
    private final DeckService deckService;
    private final DeckRepository deckRepository;

    public DeckController(DeckService deckService, DeckRepository deckRepository) {
        objectMapper = new ObjectMapper();
        this.deckService = deckService;
        this.deckRepository = deckRepository;
    }

    @Override
    public Response handleRequest(Request request) throws JsonProcessingException {
        String method = request.getMethod();
        if(method.equals("PUT")){
            return createDeck(request.getAuthorization(), request.getContent());
        }
        else if(method.equals("GET")){
            return showDeck(request.getAuthorization());
        }
        return response(
                HttpStatus.NOT_FOUND,
                ContentType.HTML, HttpStatus.NOT_FOUND.message
        );
    }

    public Response createDeck(String userId, String json) throws JsonProcessingException {
        if(deckRepository.checkDeckFull(userId)){
            return response(HttpStatus.BAD_REQUEST, ContentType.HTML, "Deck exists");
        }
        ArrayList<String> cardList = objectMapper.readValue(json, new TypeReference<ArrayList<String>>() {});
        if(cardList.size() != 4){
            return response(HttpStatus.BAD_REQUEST, ContentType.HTML, "Too few Cards");
        }
        for (int i = 0; i < cardList.size(); i++) {
            if(deckRepository.save(userId, cardList.get(i)) == false){
                return response(HttpStatus.INTERNAL_SERVER_ERROR, ContentType.HTML, HttpStatus.INTERNAL_SERVER_ERROR.message);
            }
        }
        return response(HttpStatus.OK, ContentType.HTML, HttpStatus.OK.message);
    }

    public Response showDeck(String userId){
        if(UserService.getUser(userId) != null){
            LinkedList<Card> cardList = deckRepository.getDeck(userId);
            if(cardList != null){
                return json(cardList);
            }
        }
        return response(HttpStatus.BAD_REQUEST, ContentType.HTML, "Bad Request");
    }
}
