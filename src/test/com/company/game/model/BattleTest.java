package com.company.game.model;

import com.company.game.enums.Element;
import com.company.game.enums.MonsterType;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

public class BattleTest extends TestCase {

    Battle battle;

    @BeforeEach
    public void setUp(){
        battle = new Battle();
    }

    @Test
    public void testCalculateDamage() {
        int damage1 = 15;
        int damage2 = 20;
        assertTrue(battle.calculateDamage(damage1, damage2) == 2);
    }

    @Test
    public void testCompareMonsterType() {
        MonsterCard card1 = new MonsterCard();
        MonsterCard card2 = new MonsterCard();
        card1.setMonsterType(MonsterType.FIREELF);
        card1.setDamage(20);
        card2.setMonsterType(MonsterType.DRAGON);
        card2.setDamage(40);
        assertTrue(battle.compareMonsterType(card1, card2) == 1);
    }

    @Test
    public void testCompareSpell(){
        SpellCard card1 = new SpellCard();
        MonsterCard card2 = new MonsterCard();
        card1.setElement(Element.WATER);
        card1.setDamage(20);
        card2.setElement(Element.FIRE);
        card2.setDamage(40);
        assertTrue(battle.compareSpellElement(card1, card2) == 1);
    }

}