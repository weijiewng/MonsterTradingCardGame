package com.company.game.controller;

import com.company.game.repository.BoosterRepository;
import com.company.game.service.BoosterService;
import com.company.server.Request;
import com.company.server.Response;
import com.fasterxml.jackson.core.JsonProcessingException;

public class BoosterController extends Controller{

    private final BoosterService boosterService;
    private final BoosterRepository boosterRepository;

    public BoosterController(BoosterService boosterService, BoosterRepository boosterRepository) {
        this.boosterService = boosterService;
        this.boosterRepository = boosterRepository;
    }


    @Override
    public Response handleRequest(Request request) throws JsonProcessingException {
        return null;
    }
}
