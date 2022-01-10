package com.company.game.model;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

import java.util.LinkedList;

public class DeckTest extends TestCase {
    Deck deck;
    LinkedList<Card> cards;

    @BeforeAll
    public void setUp(){
        Card card1 = new SpellCard();
        card1.setName("Spell");
        Card card2 = new MonsterCard();
        card2.setName("Monster");
        Card card3 = new MonsterCard();
        Card card4 = new SpellCard();
        cards = new LinkedList<Card>();
        cards.add(card1);
        cards.add(card2);
        cards.add(card3);
        cards.add(card4);
        deck = new Deck(cards);
    }

    @Test
    public void testGetFirstCard() {
        assertTrue(deck.getFirstCard().getName().equals("Spell"));
    }

    @Test
    public void testRemoveFirstCard() {
        deck.removeFirstCard();
        assertTrue(deck.getDeck().size() == 3);
    }




}