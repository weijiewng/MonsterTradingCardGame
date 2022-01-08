package com.company.game.model;


import java.util.ArrayList;

public class Collection {
    private ArrayList<Card> allCards;

    public Collection() {
        this.allCards = new ArrayList<Card>();
    }

    public ArrayList<Card> getAllCards() {
        return allCards;
    }

    public void addCard(Card card){
        allCards.add(card);
    }

    public void removeCard(Card card){
        allCards.remove(card);
    }
}