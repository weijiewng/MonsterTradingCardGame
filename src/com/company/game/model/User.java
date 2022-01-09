package com.company.game.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class User {

    @JsonProperty("Token")
    private String token;
    @JsonProperty("Username")
    private String username;
    @JsonProperty("Password")
    private String password;
    //TODO if login repository handles this to initialize
    @JsonProperty("Coins")
    private int coins;
    @JsonProperty("Elo")
    private int elo;
    @JsonProperty("Card List")
    private ArrayList<Card> cardList;
    @JsonProperty("Deck")
    private Deck deck;

    public User(){
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.coins = 20;
        this.elo = 100;
        this.cardList = new ArrayList<Card>();
        //TODO DECK
    }

    public User(String token, String username, String password, int coins, int elo, ArrayList<Card> cardList, Deck deck) {
        this.token = token;
        this.username = username;
        this.password = password;
        this.coins = coins;
        this.elo = elo;
        this.cardList = cardList;
        this.deck = deck;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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
