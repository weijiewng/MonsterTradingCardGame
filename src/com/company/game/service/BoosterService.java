package com.company.game.service;

import com.company.game.enums.BoosterName;
import com.company.game.model.Booster;
import com.company.game.model.Card;

import java.util.ArrayList;

public class BoosterService {

    public BoosterService() {
    }

    public Booster getBooster(String name, ArrayList<Card> cardList){
        BoosterName boosterName = BoosterName.valueOf(name);
        return new Booster(boosterName, cardList);
    }
}
