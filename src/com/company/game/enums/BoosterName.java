package com.company.game.enums;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ser.std.StdKeySerializers;

public enum BoosterName {
    @JsonProperty("Default")
    DEFAULT("Default", 5),
    @JsonProperty("DOC")
    DOC("Dimension of Chaos", 5),
    @JsonProperty("BOL")
    BOL("Brothers of Legends", 10),
    @JsonProperty("BOD")
    BOD("Burst of Destiny", 15);

    public final String name;
    public final int cost;

    BoosterName(String name, int cost) {
        this.name = name;
        this.cost = cost;
    }
}
