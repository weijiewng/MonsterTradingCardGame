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
    @JsonProperty("Coins")
    private int coins;
    @JsonProperty("Elo")
    private int elo;
    @JsonProperty("Card List")
    private ArrayList<Card> cardList;
    @JsonProperty("Deck")
    private Deck deck;
    @JsonProperty("Win")
    private int win;
    @JsonProperty("Lose")
    private int lose;
    @JsonProperty("Draw")
    private int draw;
    @JsonProperty("Name")
    private String name;
    @JsonProperty("Bio")
    private String bio;
    @JsonProperty("Image")
    private String image;

    public User(){
        this.coins = 20;
        this.elo = 100;
        this.cardList = new ArrayList<Card>();
        this.deck = new Deck();
    }

    public User(String name, String bio, String image) {
        this.coins = 20;
        this.elo = 100;
        this.cardList = new ArrayList<Card>();
        this.deck = new Deck();
        this.name = name;
        this.bio = bio;
        this.image = image;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.coins = 20;
        this.elo = 100;
        this.cardList = new ArrayList<Card>();
        this.deck = new Deck();
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

    public User(String token, String username, String password, int coins, int elo, ArrayList<Card> cardList, Deck deck, int win, int lose, int draw, String name, String bio, String image) {
        this.token = token;
        this.username = username;
        this.password = password;
        this.coins = coins;
        this.elo = elo;
        this.cardList = cardList;
        this.deck = deck;
        this.win = win;
        this.lose = lose;
        this.draw = draw;
        this.name = name;
        this.bio = bio;
        this.image = image;
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

    public int getWin() {
        return win;
    }

    public void setWin(int win) {
        this.win = win;
    }

    public int getLose() {
        return lose;
    }

    public void setLose(int lose) {
        this.lose = lose;
    }

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setDeck(Deck deck) {
        this.deck = new Deck(deck.getDeck());
    }

    public String jsonUserData(){
        return "{ \"username\" = \"" + username +
                "\", \"coins\"= " + coins +
                ", \"name\"= \"" + name +
                "\", \"bio\"= \"" + image +
                "\", \"bio\"= \"" + bio +
                "\"}";
    }

    public String jsonStats(){
        return "{\"username\" = \"" + username +
                "\", \"win\"= " + win +
                ", \"lose\"= " + lose +
                "\", \"draw\"= " + draw +
                "}";
    }

    public String jsonScore(){
        return "{\"username\" = \"" + username +
                "\", \"elo\"= " + elo +
                "\", \"win\"= " + win +
                ", \"lose\"= " + lose +
                "\", \"draw\"= " + draw +
                "}";
    }
}
