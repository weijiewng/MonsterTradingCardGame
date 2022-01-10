package com.company.game.model;

import com.company.game.enums.BoosterName;
import com.company.game.enums.Rarity;

import java.util.ArrayList;
import java.util.Random;


public class Booster {

    String id;
    BoosterName boosterName;
    ArrayList<Card> booster;
    ArrayList<Card> cardList;


    public Booster(){
        booster = new ArrayList<Card>();
    }

    public Booster(BoosterName boosterName, ArrayList<Card> cardList) {
        this.boosterName = boosterName;
        this.booster = new ArrayList<Card>();
        this.cardList = new ArrayList<>(cardList);
        createBooster();
    }

    public ArrayList<Card> getBooster() {
        return booster;
    }

    public BoosterName getBoosterName() {
        return boosterName;
    }

    public Booster(ArrayList<Card> booster) {
        this.booster = booster;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setBoosterName(BoosterName boosterName) {
        this.boosterName = boosterName;
    }

    public void addCard(Card card){
        booster.add(card);
    }

    private void createBooster() {
        ArrayList<Card> legendaryCards;
        ArrayList<Card> ultraRareCards;
        ArrayList<Card> rareCards;
        ArrayList<Card> commonCards;

        legendaryCards = new ArrayList<Card>();
        ultraRareCards = new ArrayList<Card>();
        rareCards = new ArrayList<Card>();
        commonCards = new ArrayList<Card>();

        for (int i = 0; i < cardList.size(); i++) {
            switch(cardList.get(i).getRarity()){
                case LEGENDARY:
                    legendaryCards.add(cardList.get(i));
                case ULTRA_RARE:
                    ultraRareCards.add(cardList.get(i));
                case RARE:
                    rareCards.add(cardList.get(i));
                case COMMON:
                    commonCards.add(cardList.get(i));
            }
        }

        Random rand = new Random();
        int rarity, cardNumber;

        for (int i = 0; i < 5; i++) {
            rarity = rand.nextInt(100);
            if(boosterName == BoosterName.DOC){
                if(rarity < 5){
                    cardNumber = rand.nextInt(legendaryCards.size() - 1);
                    booster.add(legendaryCards.get(cardNumber));
                }
                else if(rarity < 15){
                    cardNumber = rand.nextInt(ultraRareCards.size() - 1);
                    booster.add(ultraRareCards.get(cardNumber));
                }
                else if(rarity < 45){
                    cardNumber = rand.nextInt(rareCards.size() - 1);
                    booster.add(rareCards.get(cardNumber));
                }
                else{
                    cardNumber = rand.nextInt(commonCards.size() - 1);
                    booster.add(commonCards.get(cardNumber));
                }
            }
            else if(boosterName == BoosterName.BOL){
                if(rarity < 10){
                    cardNumber = rand.nextInt(legendaryCards.size() - 1);
                    booster.add(legendaryCards.get(cardNumber));
                }
                else if(rarity < 25){
                    cardNumber = rand.nextInt(ultraRareCards.size() - 1);
                    booster.add(ultraRareCards.get(cardNumber));
                }
                else if(rarity < 65){
                    cardNumber = rand.nextInt(rareCards.size() - 1);
                    booster.add(rareCards.get(cardNumber));
                }
                else{
                    cardNumber = rand.nextInt(commonCards.size() - 1);
                    booster.add(commonCards.get(cardNumber));
                }
            }
            else if(boosterName == BoosterName.BOD){
                if(rarity < 20){
                    cardNumber = rand.nextInt(legendaryCards.size() - 1);
                    booster.add(legendaryCards.get(cardNumber));
                }
                else if(rarity < 45){
                    cardNumber = rand.nextInt(ultraRareCards.size() - 1);
                    booster.add(ultraRareCards.get(cardNumber));
                }
                else if(rarity < 60){
                    cardNumber = rand.nextInt(rareCards.size() - 1);
                    booster.add(rareCards.get(cardNumber));
                }
                else{
                    cardNumber = rand.nextInt(commonCards.size() - 1);
                    booster.add(commonCards.get(cardNumber));
                }
            }
        }

    }
}
