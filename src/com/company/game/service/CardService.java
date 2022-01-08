package com.company.game.service;

import com.company.game.model.Card;

import java.util.ArrayList;

public class CardService {
    private ArrayList<Card> cardList;

    public CardService(ArrayList<Card> cardList) {
        this.cardList = cardList;
    }

    public void addCard(Card card){
        cardList.add(card);
    }
}
