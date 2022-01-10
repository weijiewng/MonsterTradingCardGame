package com.company.game.controller;

import com.company.game.model.Battle;
import com.company.game.model.Card;
import com.company.game.model.Deck;
import com.company.game.model.User;
import com.company.game.repository.BattleRepository;
import com.company.game.repository.DeckRepository;
import com.company.game.service.BattleService;
import com.company.game.service.UserService;
import com.company.server.Request;
import com.company.server.Response;
import com.company.server.http.ContentType;
import com.company.server.http.HttpStatus;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.LinkedList;

public class BattleController extends Controller{

    private final BattleService battleService;
    private final BattleRepository battleRepository;
    private final DeckRepository deckRepository;

    public BattleController(BattleService battleService, BattleRepository battleRepository, DeckRepository deckRepository) {
        this.battleService = battleService;
        this.battleRepository = battleRepository;
        this.deckRepository = deckRepository;
    }

    @Override
    public Response handleRequest(Request request) throws JsonProcessingException {
        if(request.getMethod().equals("POST")){
            if(UserService.checkIfUserIsLoggedIn(request.getAuthorization())){
                return startBattle(request.getAuthorization());
            }
        }
        return response(
                HttpStatus.NOT_FOUND,
                ContentType.HTML, HttpStatus.NOT_FOUND.message
        );
    }

    public Response startBattle(String token){
        User user = UserService.getUser(token);
        Deck deck = new Deck(deckRepository.getDeck(token));
        if(deck != null){
            user.setDeck(deck);
            Battle battle = battleService.startLookingForBattle(user);
            if(battle != null){
                setUserStatus(battle, user.getToken());
                return response(HttpStatus.OK, ContentType.HTML, battle.getLog().toString());
            }
            return response(HttpStatus.INTERNAL_SERVER_ERROR, ContentType.HTML, "No user found for battle");
        }
        return response(HttpStatus.BAD_REQUEST, ContentType.HTML, "Bad Request");
    }

    private void setUserStatus(Battle battle, String userToken){
        if(battle.getResult() == 1){
            if(userToken == battle.getPlayer1().getToken()){
                battleRepository.win(userToken);
            }
            else{
                battleRepository.lose(userToken);
            }
        }
        else if(battle.getResult() == 2){
            if(userToken == battle.getPlayer1().getToken()){
                battleRepository.lose(userToken);
            }
            else{
                battleRepository.win(userToken);
            }
        }
        else{
            battleRepository.draw(userToken);
        }
    }
}
