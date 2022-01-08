package com.company.game.model;

import com.company.game.enums.Element;
import com.company.game.enums.Rarity;

public abstract class Card {
    private String id;
    private String name;
    private int damage;
    private Element element;
    private Rarity rarity;

    public Card(String id, String name, int damage, Element element, Rarity rarity){
        this.id = id;
        this.name = name;
        this.damage = damage;
        this.element = element;
        this.rarity = rarity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public Element getElement() {
        return element;
    }

    public void setElement(Element element) {
        this.element = element;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public void setRarity(Rarity rarity) {
        this.rarity = rarity;
    }

    public String toString() {
        return "name='" + name +
                ", damage=" + damage +
                ", element=" + element +
                ", rarity=" + rarity;
    }
}

