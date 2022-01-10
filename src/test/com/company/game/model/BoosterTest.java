package com.company.game.model;

import com.company.game.enums.BoosterName;
import com.company.game.enums.Rarity;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;

public class BoosterTest extends TestCase {
    ArrayList<Card> cards;
    Booster booster;
    @BeforeAll
    public void setUp(){
        booster = new Booster();
        Card legendary = new MonsterCard();
        legendary.setRarity(Rarity.LEGENDARY);
        Card ultraRare = new SpellCard();
        ultraRare.setRarity(Rarity.ULTRA_RARE);
        Card rare = new MonsterCard();
        rare.setRarity(Rarity.RARE);
        Card common = new SpellCard();
        common.setRarity(Rarity.COMMON);
        cards = new ArrayList<>();
        cards.add(legendary);
        cards.add(legendary);
        cards.add(ultraRare);
        cards.add(ultraRare);
        cards.add(rare);
        cards.add(rare);
        cards.add(common);
        cards.add(common);
    }
    @Test
    public void testAddCard(){
        Card card = new SpellCard();
        booster.addCard(card);
        assertEquals(booster.getBooster().get(0), card);
    }

    @Test
    public void testCreateBoosterDOC(){
        booster = new Booster(BoosterName.DOC, cards);
        assertTrue(booster.getBooster().size() == 5);
    }
    @Test
    public void testCreateBoosterBOL(){
        booster = new Booster(BoosterName.BOL, cards);
        assertTrue(booster.getBooster().size() == 5);
    }
    @Test
    public void testCreateBoosterBOD(){
        booster = new Booster(BoosterName.BOD, cards);
        assertTrue(booster.getBooster().size() == 5);
    }

}