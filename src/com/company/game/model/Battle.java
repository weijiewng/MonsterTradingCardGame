package com.company.game.model;

import com.company.game.enums.Element;

public class Battle {
    private final int maxCombatRound = 100;
    private User player1;
    private User player2;
    private Deck deck1;
    private Deck deck2;
    private int result;

    public Battle(User player1, User player2, Deck deck1, Deck deck2) {
        this.player1 = player1;
        this.player2 = player2;
        this.deck1 = deck1;
        this.deck2 = deck2;
    }

    public User getPlayer1() {
        return player1;
    }

    public User getPlayer2() {
        return player2;
    }

    public Deck getDeck1() {
        return deck1;
    }

    public Deck getDeck2() {
        return deck2;
    }

    public int getResult() {
        return result;
    }

    public void doBattle(){
        Card card1;
        Card card2;
        int compareResult;
        result = 0; //DRAW
        //TODO Log the whole Battle
        for (int i = 1; i < maxCombatRound; i++) {
            if(deck1.getDeck().size() == 0){
                result = 2; //Player 2 won
                break;
            }
            else if(deck2.getDeck().size() == 0){
                result = 1; //Player 1 won
                break;
            }
            card1 = deck1.getFirstCard();
            card2 = deck2.getFirstCard();
            if(card1 instanceof SpellCard || card2 instanceof SpellCard) {
                compareResult = compareSpellElement(card1, card2);
            }
            else{
                compareResult = compareMonsterType(card1, card2);
            }
            exchangeCards(card1, card2, compareResult);
        }
    }

    private int compareSpellElement(Card card1, Card card2){
        int damage1 = 0;
        int damage2 = 0;
        Element element1 = card1.getElement();
        Element element2 = card2.getElement();
        if(element1 == element2){
            damage1 = card1.getDamage();
            damage2 = card2.getDamage();
        }
        else{
            switch(element1){
                case WATER: {
                    if(element2 == Element.FIRE){
                        damage1 = card1.getDamage() * 2;
                        damage2 = card2.getDamage() / 2;
                    }
                    else if(element2 == Element.GRASS){
                        damage1 = card1.getDamage() / 2;
                        damage2 = card2.getDamage() * 2;
                    }
                }
                case FIRE: {
                    if(element2 == Element.WATER){
                        damage1 = card1.getDamage() / 2;
                        damage2 = card2.getDamage() * 2;
                    }
                    else if(element2 == Element.GRASS){
                        damage1 = card1.getDamage() * 2;
                        damage2 = card2.getDamage() / 2;
                    }
                }
                case GRASS: {
                    if(element2 == Element.WATER){
                        damage1 = card1.getDamage() * 2;
                        damage2 = card2.getDamage() / 2;
                    }
                    else if(element2 == Element.FIRE){
                        damage1 = card1.getDamage() / 2;
                        damage2 = card2.getDamage() * 2;
                    }
                }
            }
        }
        return calculateDamage(damage1, damage2);
    }

    private int compareMonsterType(Card card1, Card card2){
        //TODO otherewise compare type
        return calculateDamage(card1.getDamage(), card2.getDamage());
    }

    private int calculateDamage(int damage1, int damage2){
        //TODO Type effects
        if(damage1 > damage2){
            return 1;
        }
        else if(damage1 < damage2){
            return 2;
        }
        return 0;
    }

    private void exchangeCards(Card card1, Card card2, int compareResult){
        deck1.removeFirstCard();
        deck2.removeFirstCard();

        if(compareResult == 1){
            deck1.addCard(card1);
            deck1.addCard(card2);
        }
        else if(compareResult == 2){
            deck2.addCard(card2);
            deck2.addCard(card1);
        }
        else{
            deck1.addCard(card1);
            deck2.addCard(card2);
        }
    }
}
