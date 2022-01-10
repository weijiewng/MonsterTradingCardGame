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
    private TradingController tradingController;
    public GameApi() {
        this.userController = new UserController(new UserRepository());
        this.cardController = new CardController(new CardRepository());
        this.boosterController = new BoosterController(new BoosterService(), new BoosterRepository(), new CardRepository());
        this.battleController = new BattleController(new BattleService(), new BattleRepository(), new DeckRepository());
        this.deckController = new DeckController(new DeckRepository());
        this.transactionController = new TransactionController(new TransactionRepository());
        this.tradingController = new TradingController(new TradingRepository());
    }

    @Override
    public Response handleRequest(Request request) throws JsonProcessingException {
        String route = request.getRoute();
        if(route.contains("/users")){
            return userController.handleRequest(request);
        }
        else if(route.contains("/sessions")){
            return userController.handleRequest(request);
        }
        else if(route.contains("/stats")){
            return userController.handleRequest(request);
        }
        else if(route.contains("/score")){
            return userController.handleRequest(request);
        }
        else if(route.contains("/cards")){
            return cardController.handleRequest(request);
        }
        else if(route.contains("/transactions/boosters")){
            return transactionController.handleRequest(request);
        }
        else if(route.contains("/boosters")){
            return boosterController.handleRequest(request);
        }
        else if(route.contains("/battles")){
            return battleController.handleRequest(request);
        }
        else if(route.contains("/decks")){
            return deckController.handleRequest(request);
        }
        else if(route.contains("/tradings")){
            return tradingController.handleRequest(request);
        }
        Response response = new Response();
        response.setStatus(HttpStatus.NOT_FOUND);
        response.setContentType(ContentType.HTML);
        response.setContent("Command not found");
        return response;
    }
}
