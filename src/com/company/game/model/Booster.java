package com.company.game.model;

import java.util.ArrayList;

public class Booster {

    String name;
    ArrayList<Card> booster;

    public Booster(){
        booster = new ArrayList<Card>();
    }

    public Booster(String name){
        this.name = name;
        //TODO Create booster methode depenedant on name
    }
    public ArrayList<Card> getBooster() {
        return booster;
    }

    public Booster(ArrayList<Card> booster) {
        this.booster = booster;
    }

    public void addCard(Card card){
        booster.add(card);
    }
}
