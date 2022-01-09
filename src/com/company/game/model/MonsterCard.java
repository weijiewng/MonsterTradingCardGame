package com.company.game.model;

import com.company.game.enums.Element;
import com.company.game.enums.Rarity;
import com.company.game.enums.MonsterType;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MonsterCard extends Card {
    @JsonProperty("Type")
    private MonsterType monsterType;

    public MonsterCard(){

    }
    public MonsterCard(String id, String name, int damage, Element element, Rarity rarity, MonsterType monsterType) {
        super(id, name, damage, element, rarity);
        this.monsterType = monsterType;
    }

    public MonsterType getMonsterType() {
        return monsterType;
    }

    public void setMonsterType(MonsterType type) {
        this.monsterType = type;
    }

    @Override
    public String toString() {
        return "MonsterCard{" +
                super.toString() +
                ", MonsterType=" + monsterType +
                '}';
    }
}
