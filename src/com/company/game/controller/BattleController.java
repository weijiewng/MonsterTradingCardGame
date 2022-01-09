package com.company.game.controller;

import com.company.game.repository.BattleRepository;
import com.company.game.service.BattleService;
import com.company.server.Request;
import com.company.server.Response;
import com.fasterxml.jackson.core.JsonProcessingException;

public class BattleController extends Controller{

    private final BattleService battleService;
    private final BattleRepository battleRepository;

    public BattleController(BattleService battleService, BattleRepository battleRepository) {
        this.battleService = battleService;
        this.battleRepository = battleRepository;
    }

    @Override
    public Response handleRequest(Request request) throws JsonProcessingException {
        return null;
    }
}
