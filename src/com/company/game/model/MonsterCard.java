package com.company.game.model;

import com.company.game.enums.Element;
import com.company.game.enums.Rarity;
import com.company.game.enums.Type;

public class MonsterCard extends Card {
    private Type type;

    public MonsterCard(String name, int damage, Element element, Rarity rarity, Type type) {
        super(name, damage, element, rarity);
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "MonsterCard{" +
                super.toString() +
                ", type=" + type +
                '}';
    }
}
