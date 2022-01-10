package com.company.game.controller;

import com.company.game.enums.BoosterName;
import com.company.game.model.Booster;
import com.company.game.model.Card;
import com.company.game.model.MonsterCard;
import com.company.game.model.User;
import com.company.game.repository.BoosterRepository;
import com.company.game.repository.CardRepository;
import com.company.game.service.BoosterService;
import com.company.game.service.UserService;
import com.company.server.Request;
import com.company.server.Response;
import com.company.server.http.ContentType;
import com.company.server.http.HttpStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;


public class BoosterController extends Controller{

    ObjectMapper objectMapper;
    private final BoosterService boosterService;
    private final BoosterRepository boosterRepository;
    private final CardRepository cardRepository;

    public BoosterController(BoosterService boosterService, BoosterRepository boosterRepository, CardRepository cardRepository) {
        objectMapper = new ObjectMapper();
        this.boosterService = boosterService;
        this.boosterRepository = boosterRepository;
        this.cardRepository = cardRepository;
    }

    @Override
    public Response handleRequest(Request request) throws JsonProcessingException {
        if(request.getMethod().equals("POST")){
            if(request.getAuthorization().equals("Basic admin-mtcgToken")){
                return savePackage(request.getContent());
            }
        }
        return response(
                HttpStatus.NOT_FOUND,
                ContentType.HTML, HttpStatus.NOT_FOUND.message
        );
    }

    private Response savePackage(String json) throws JsonProcessingException {
        ArrayList<Card> cardList =  cardRepository.getAllCards();
        JsonNode jsonNode = objectMapper.readTree(json);
        String boosterName = jsonNode.get("Booster Name").asText();
        if(boosterName.equals("DEFAULT")){
            String jsonCardId = jsonNode.get("cardIds").toString();
            String[] stringIds = objectMapper.readValue(jsonNode.get("cardIds").toString(), String[].class);
            ArrayList cardIdList = new ArrayList(Arrays.asList(stringIds));
            if(boosterRepository.saveBooster(cardIdList)){
                return response(HttpStatus.OK, ContentType.HTML, "Created Default" );
            }
            return response(HttpStatus.INTERNAL_SERVER_ERROR, ContentType.HTML, HttpStatus.INTERNAL_SERVER_ERROR.message);
        }
        Booster booster = boosterService.getBooster(boosterName, cardList);
        if(boosterRepository.saveBooster(booster.getBooster(), booster.getBoosterName().name, booster.getBoosterName().cost)){
            return response(HttpStatus.OK, ContentType.HTML, "Created " + booster.getBoosterName().name);
        }
        return response(HttpStatus.BAD_REQUEST, ContentType.HTML, HttpStatus.BAD_REQUEST.message);
    }
}
