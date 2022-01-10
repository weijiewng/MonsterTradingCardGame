package com.company.game.service;

import com.company.game.enums.BoosterName;
import com.company.game.enums.Rarity;
import com.company.game.model.Booster;
import com.company.game.model.Card;
import com.company.game.model.MonsterCard;
import com.company.game.model.SpellCard;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;

public class BoosterServiceTest extends TestCase {

    BoosterService boosterService;
    @Test
    public void testGetBooster() {
        boosterService = new BoosterService();
        Card legendary = new MonsterCard();
        legendary.setRarity(Rarity.LEGENDARY);
        Card ultraRare = new SpellCard();
        ultraRare.setRarity(Rarity.ULTRA_RARE);
        Card rare = new MonsterCard();
        rare.setRarity(Rarity.RARE);
        Card common = new SpellCard();
        common.setRarity(Rarity.COMMON);
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(legendary);
        cards.add(legendary);
        cards.add(ultraRare);
        cards.add(ultraRare);
        cards.add(rare);
        cards.add(rare);
        cards.add(common);
        cards.add(common);
        Booster booster = boosterService.getBooster("DOC", cards);
        assertEquals(booster.getBoosterName().name, "Dimension of Chaos");
    }
}