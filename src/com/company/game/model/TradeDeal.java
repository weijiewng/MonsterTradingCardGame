package com.company.game.model;

import com.company.game.enums.Element;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TradeDeal {
    @JsonProperty("Id")
    private String id;
    @JsonProperty("Card Id")
    private String cardId;
    @JsonProperty("Minimum Damage")
    private int minDamage;
    @JsonProperty("Element")
    private Element element;
    @JsonProperty("User Id")
    private String userId;

    public TradeDeal(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public int getMinDamage() {
        return minDamage;
    }

    public void setMinDamage(int minDamage) {
        this.minDamage = minDamage;
    }

    public Element getElement() {
        return element;
    }

    public void setElement(Element element) {
        this.element = element;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}



