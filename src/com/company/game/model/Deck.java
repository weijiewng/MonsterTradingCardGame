package com.company.game.model;

import java.util.LinkedList;

public class Deck {
    private LinkedList<Card> deck;

    public Deck() {
        this.deck = new LinkedList<Card>();
    }

    public Deck(LinkedList<Card> deck){
        this.deck = new LinkedList<>(deck);
    }

    public LinkedList<Card> getDeck() {
        return deck;
    }

    public Card getFirstCard(){
        return deck.getFirst();
    }

    public void removeFirstCard(){
        deck.removeFirst();
    }

    public void addCard(Card card){
        deck.add(card);
    }

    public void removeCard(Card card){
        deck.remove(card);
    }
}
