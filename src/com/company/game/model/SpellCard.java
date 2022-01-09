package com.company.game.model;

import com.company.game.enums.Element;
import com.company.game.enums.Rarity;

public class SpellCard extends Card{

    public SpellCard(){

    }

    public SpellCard(String id, String name, int damage, Element element, Rarity rarity) {
        super(id, name, damage, element, rarity);
    }

    @Override
    public String toString() {
        return "SpellCard{" +
                super.toString() +
                '}';

    }
}
