package com.company.game.model;

import java.util.ArrayList;

public class User {
    private String username;
    private String password;
    private int coins;
    private int elo;
    private ArrayList<Card> cardList;
    private Deck deck;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.coins = 20;
        this.elo = 100;
        this.cardList = new ArrayList<Card>();
        //TODO DECK
    }

    public User(String username, String password, int coins, int elo, ArrayList<Card> cardList, Deck deck) {
        this.username = username;
        this.password = password;
        this.coins = coins;
        this.elo = elo;
        this.cardList = cardList;
        this.deck = deck;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public int getElo() {
        return elo;
    }

    public void setElo(int elo) {
        this.elo = elo;
    }

    public ArrayList<Card> getCardList() {
        return cardList;
    }

    public void setCardList(ArrayList<Card> cardList) {
        this.cardList = cardList;
    }

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public void minusCoins(int amount){
        this.coins -= amount;
    }
}
