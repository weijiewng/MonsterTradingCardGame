package com.company.game.controller;

import com.company.game.repository.CardRepository;
import com.company.game.service.CardService;
import com.company.server.Request;
import com.company.server.Response;
import com.company.server.http.ContentType;
import com.company.server.http.HttpStatus;

public class CardController extends Controller{

    private final CardService cardService;
    private final CardRepository cardRepository;

    public CardController(CardService cardService, CardRepository cardRepository) {
        this.cardService = cardService;
        this.cardRepository = cardRepository;
    }

    @Override
    public Response handleRequest(Request request) {
        if(request.getMethod().equals("POST")){

        }
        return response(
                HttpStatus.NOT_FOUND,
                ContentType.JSON,
                "{ \"error\": \"Not Found\"}"
        );
    }


}
