package com.company.game.model;

import com.company.game.enums.Element;
import com.company.game.enums.MonsterType;
import com.company.game.util.Toolbox;

import java.util.UUID;

public class Battle {
    private final int maxCombatRound = 100;
    private User player1;
    private User player2;
    private Deck deck1;
    private Deck deck2;
    private int result;
    private boolean status;
    StringBuilder log;

    public Battle(){
        log = new StringBuilder();
    }

    public Battle(User player1, User player2, Deck deck1, Deck deck2) {
        this.player1 = player1;
        this.player2 = player2;
        this.deck1 = new Deck(deck1.getDeck());
        this.deck2 = new Deck(deck2.getDeck());
        log = new StringBuilder();
    }
    public Battle(User player1, Deck deck1){
        this.player1 = player1;
        this.deck1 = new Deck(deck1.getDeck());
        log = new StringBuilder();
    }

    public User getPlayer1() {
        return player1;
    }

    public User getPlayer2() {
        return player2;
    }

    public void setPlayer2(User player2) {
        this.player2 = player2;
    }

    public void setDeck2(Deck deck2) {
        this.deck2 = new Deck(deck2.getDeck());
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

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public StringBuilder getLog(){
        return log;
    }

    public void doBattle(){
        Card card1;
        Card card2;
        int compareResult;
        result = 0;
        for (int i = 1; i < maxCombatRound; i++) {
            if(deck1.getDeck().size() == 0){
                result = 2; //Player 2 won
                log.append(player2.getUsername());
                log.append(" wins\n\n");
                break;
            }
            else if(deck2.getDeck().size() == 0){
                result = 1; //Player 1 won
                log.append(player1.getUsername());
                log.append(" wins\n\n");
                break;
            }
            log.append(player1.getUsername());
            log.append(" Cards: ");
            log.append(deck1.getDeck().size());
            log.append("\n");
            log.append(player2.getUsername());
            log.append(" Cards: ");
            log.append(deck2.getDeck().size());
            card1 = deck1.getFirstCard();
            card2 = deck2.getFirstCard();
            log.append("\nTurn ");
            log.append(i);
            log.append(": ");
            log.append(player1.getUsername());
            log.append(": ");
            log.append(card1.getName());
            log.append(" (");
            log.append(card1.getElement());
            log.append(") vs ");
            log.append(player2.getUsername());
            log.append(": ");
            log.append(card2.getName());
            log.append(" (");
            log.append(card2.getElement());
            log.append(") => ");
            log.append(card1.getDamage());
            log.append(" vs ");
            log.append(card2.getDamage());
            log.append(" => ");
            if(card1 instanceof SpellCard || card2 instanceof SpellCard) {
                compareResult = compareSpellElement(card1, card2);
            }
            else{
                compareResult = compareMonsterType(card1, card2);
            }
            exchangeCards(card1, card2, compareResult);
        }
        if(result == 0){
            log.append("DRAW\n\n");
        }
    }

    public int compareSpellElement(Card card1, Card card2){
        int damage1 = card1.getDamage();
        int damage2 = card2.getDamage();
        Element element1 = card1.getElement();
        Element element2 = card2.getElement();

        if(!element1.equals(element2)){
            switch (element1) {
                case WATER: {
                    if (element2 == Element.FIRE) {
                        damage1 *= 2;
                        damage2 /= 2;
                        break;
                    } else if (element2 == Element.GRASS) {
                        damage1 /= 2;
                        damage2 *= 2;
                        break;
                    }
                }
                case FIRE: {
                    if (element2 == Element.WATER) {
                        damage1 /=  2;
                        damage2 *=  2;
                        break;
                    } else if (element2 == Element.GRASS) {
                        damage1 *= 2;
                        damage2 /= 2;
                        break;
                    }
                }
                case GRASS: {
                    if (element2 == Element.WATER) {
                        damage1 *= 2;
                        damage2 /= 2;
                        break;
                    } else if (element2 == Element.FIRE) {
                        damage1 /= 2;
                        damage2 *=  2;
                        break;
                    }
                }
            }
        }
        return calculateDamage(damage1, damage2);
    }

    public int compareMonsterType(Card card1, Card card2){
        int damage1 = card1.getDamage();
        int damage2 = card2.getDamage();
        MonsterType type1 = ((MonsterCard)card1).getMonsterType();
        MonsterType type2 = ((MonsterCard)card2).getMonsterType();

        if(type1 == MonsterType.GOBLIN){
            if(type2 == MonsterType.DRAGON){
                damage1 = 0;
                log.append("Goblins are too afraid of Dragons ->");
            }
            else{
                damage1 = card1.getDamage();
            }
            damage2 = card2.getDamage();
        }
        else if(type1 == MonsterType.FIREELF){
            if(type2 == MonsterType.DRAGON) {
                damage2 = 0;
                log.append("FireElves know Dragons since they were little ->");
            }
            else{
                damage2 = card2.getDamage();
            }
            damage1 = card2.getDamage();
        }
        else if (type1 == MonsterType.WIZARD) {
            if(type2 == MonsterType.ORK){
                damage2 = 0;
                log.append("Wizards can control Orks ->");
            }
            else{
                damage2 = card2.getDamage();
            }
            damage1 = card2.getDamage();
        }
        else if(type2 == MonsterType.GOBLIN){
            if(type1 == MonsterType.DRAGON){
                damage2 = 0;
                log.append("Goblins are too afraid of Dragons ->");
            }
            else{
                damage2 = card2.getDamage();
            }
            damage1 = card2.getDamage();
        }
        else if(type2 == MonsterType.FIREELF){
            if(type1 == MonsterType.DRAGON){
                damage1 = 0;
                log.append("FireElves know Dragons since they were little ->");
            }
            else{
                damage1 = card1.getDamage();
            }
            damage2 = card2.getDamage();
        }
        else if (type2 == MonsterType.WIZARD) {
            if(type1 == MonsterType.ORK){
                damage1 = 0;
                log.append("Wizards can control Orks -> ");
            }
            else{
                damage1 = card1.getDamage();
            }
            damage2 = card2.getDamage();
        }
        return calculateDamage(damage1, damage2);
    }

    public int calculateDamage(int damage1, int damage2){
        log.append(damage1);
        log.append(" vs ");
        log.append(damage2);
        if(damage1 > damage2){
            log.append(" -> Player1 card wins\n");
            return 1;
        }
        else if(damage1 < damage2){
            log.append(" -> Player2 card wins\n");
            return 2;
        }
        log.append(" -> Draw\n");
        return 0;
    }

    public void exchangeCards(Card card1, Card card2, int compareResult){
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
