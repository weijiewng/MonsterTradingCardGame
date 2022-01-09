package com.company.game.controller;

import com.company.game.model.Card;
import com.company.game.model.MonsterCard;
import com.company.game.model.SpellCard;
import com.company.game.repository.CardRepository;
import com.company.game.service.CardService;
import com.company.game.util.Toolbox;
import com.company.server.Request;
import com.company.server.Response;
import com.company.server.http.ContentType;
import com.company.server.http.HttpStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


public class CardController extends Controller{

    private final CardService cardService;
    private final CardRepository cardRepository;

    public CardController(CardService cardService, CardRepository cardRepository) {
        this.cardService = cardService;
        this.cardRepository = cardRepository;
    }

    @Override
    public Response handleRequest(Request request) throws JsonProcessingException {
        if(request.getMethod().equals("POST")){
            if(request.getAuthorization().equals("Basic admin-mtcgToken")){
                return addCard(request.getContent());
            }
        }
        else if(request.getMethod().equals("GET")){
            
        }
        return response(
                HttpStatus.NOT_FOUND,
                ContentType.JSON,
                "{ \"error\": \"Not Found\"}"
        );
    }
    public Response addCard(String json) throws JsonProcessingException {
        Card card;

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(json);

        if(jsonNode.get("Type").asText() != null){
            card = objectMapper.readValue(json, MonsterCard.class);
        }
        else{
            card = objectMapper.readValue(json, SpellCard.class);
        }

        card.setId(Toolbox.createUUID());
        card = cardRepository.save(card);
        if(card != null){
            return response(HttpStatus.OK, ContentType.JSON, card.getId());
        }
        return response(HttpStatus.BAD_REQUEST, ContentType.HTML, "Bad Request");
    }

}
