package com.company.game.controller;

import com.company.game.model.TradeDeal;
import com.company.game.model.User;
import com.company.game.repository.TradingRepository;
import com.company.game.service.UserService;
import com.company.server.Request;
import com.company.server.Response;
import com.company.server.http.ContentType;
import com.company.server.http.HttpStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;

public class TradingController extends Controller{

    ObjectMapper objectMapper;
    private TradingRepository tradingRepository;

    public TradingController(TradingRepository tradingRepository) {
        objectMapper = new ObjectMapper();
        this.tradingRepository = tradingRepository;
    }

    @Override
    public Response handleRequest(Request request) throws JsonProcessingException {
        String route = request.getRoute();
        if(request.getMethod().equals("GET")){
            if(UserService.checkIfUserIsLoggedIn(request.getAuthorization())){
                return getTradeDeals(request.getAuthorization());
            }
        }
        else if(request.getMethod().equals("POST")){
            if(UserService.checkIfUserIsLoggedIn(request.getAuthorization())){
                String[] split = route.split("/");
                String tradeId = split[split.length - 1];
                if(!tradeId.equals("tradings")){
                    return trade(request.getAuthorization(), tradeId, request.getContent());
                }
                return createTradeDeal(request.getAuthorization(), request.getContent());
            }
        }
        else if(request.getMethod().equals("DELETE")){
            if(UserService.checkIfUserIsLoggedIn(request.getAuthorization())){
                String[] split = route.split("/");
                String tradeId = split[split.length - 1];
                return deleteTradeDeal(request.getAuthorization(), tradeId);
            }
        }
        return response(
                HttpStatus.NOT_FOUND,
                ContentType.HTML, HttpStatus.NOT_FOUND.message
        );
    }

    private Response getTradeDeals(String token){
        ArrayList<TradeDeal> tradeDeals = tradingRepository.getTradeDeals(token);
        if(tradeDeals != null){
            return json(tradeDeals);
        }
        return response(HttpStatus.BAD_REQUEST, ContentType.HTML, HttpStatus.BAD_REQUEST.message);
    }

    private Response createTradeDeal(String token, String json) throws JsonProcessingException {
        TradeDeal tradeDeal = objectMapper.readValue(json, TradeDeal.class);
        tradeDeal = tradingRepository.createTradeDeal(token, tradeDeal);
        if(tradeDeal != null){
            return response(HttpStatus.OK, ContentType.HTML, tradeDeal.getId());
        }
        return response(HttpStatus.BAD_REQUEST, ContentType.HTML, HttpStatus.BAD_REQUEST.message);
    }

    private Response deleteTradeDeal(String token, String tradeId){
        if(tradingRepository.deleteTradeDeal(token, tradeId)){
            return response(HttpStatus.OK, ContentType.HTML, HttpStatus.OK.message);
        }
        return response(HttpStatus.INTERNAL_SERVER_ERROR, ContentType.HTML, HttpStatus.INTERNAL_SERVER_ERROR.message);
    }

    private Response trade(String token, String tradeId, String content) throws JsonProcessingException {
        String cardId = content.replaceAll("\"", "");
        if(tradingRepository.trade(token, tradeId, cardId)){
            return response(HttpStatus.OK, ContentType.HTML, HttpStatus.OK.message);
        }
        return response(HttpStatus.BAD_REQUEST, ContentType.HTML, HttpStatus.BAD_REQUEST.message);
    }
}
