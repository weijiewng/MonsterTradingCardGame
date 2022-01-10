package com.company.game;

import com.company.game.controller.*;
import com.company.game.repository.*;
import com.company.game.service.*;
import com.company.server.Request;
import com.company.server.Response;
import com.company.server.ServerApplication;
import com.company.server.http.ContentType;
import com.company.server.http.HttpStatus;
import com.fasterxml.jackson.core.JsonProcessingException;

public class GameApi implements ServerApplication {

    private UserController userController;
    private CardController cardController;
    private BoosterController boosterController;
    private BattleController battleController;
    private DeckController deckController;
    private TransactionController transactionController;
    public GameApi() {
        this.userController = new UserController(new UserRepository());
        this.cardController = new CardController(new CardService(), new CardRepository());
        this.boosterController = new BoosterController(new BoosterService(), new BoosterRepository(), new CardRepository());
        this.battleController = new BattleController(new BattleService(), new BattleRepository(), new DeckRepository());
        this.deckController = new DeckController(new DeckService(), new DeckRepository());
        this.transactionController = new TransactionController(new TransactionRepository());
    }

    @Override
    public Response handleRequest(Request request) throws JsonProcessingException {
        String route = request.getRoute();
        switch(route){
            case "/users":
            case "/sessions": {
                return userController.handleRequest(request);
            }
            case "/cards":{
                return cardController.handleRequest(request);
            }
            case "/boosters":{
                return boosterController.handleRequest(request);
            }
            case "/transactions/boosters":{
                return transactionController.handleRequest(request);
            }
            case "/battles":{
                return battleController.handleRequest(request);
            }
            case "/decks":{
                return deckController.handleRequest(request);
            }
        }
        Response response = new Response();
        response.setStatus(HttpStatus.NOT_FOUND);
        response.setContentType(ContentType.HTML);
        response.setContent("Command not found");
        return response;
    }
}
