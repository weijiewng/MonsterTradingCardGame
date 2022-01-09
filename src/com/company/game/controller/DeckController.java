package com.company.game.controller;

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


public class DeckController extends Controller{

    ObjectMapper objectMapper;
    private final DeckService deckService;
    private final DeckRepository deckRepository;

    public DeckController(DeckService deckService, DeckRepository deckRepository) {
        objectMapper  = new ObjectMapper();
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

        }
        return null;
    }

    public Response createDeck(String userId, String json) throws JsonProcessingException {

        ArrayList<String> cardList = objectMapper.readValue(json, new TypeReference<ArrayList<String>>() {});
        for (int i = 0; i < cardList.size(); i++) {
            if(deckRepository.save(userId, cardList.get(i)) == null){
                return response(HttpStatus.BAD_REQUEST, ContentType.HTML, "Bad Request");
            }
        }
        return response(HttpStatus.OK, ContentType.JSON, "hello: hello");
    }
}
