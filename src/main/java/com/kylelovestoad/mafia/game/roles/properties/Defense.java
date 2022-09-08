package com.kylelovestoad.mafia.game.roles.properties;

import java.util.HashMap;

public enum Defense {
    NONE(0),
    BASIC(1),
    POWERFUL(2),
    INVINCIBLE(3);

    final int power;
    static final HashMap<Integer, Defense> BY_POWER = new HashMap<>();
    Defense(int power) {
        this.power = power;
    }

    public int getPower() {
        return power;
    }

    public Defense getByValue(int value) {
        return BY_POWER.get(value);
    }

    static {
        for (Defense defense : values()) {
            BY_POWER.put(defense.getPower(), defense);
        }
    }
}
