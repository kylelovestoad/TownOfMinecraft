package com.kylelovestoad.mafia.game.roles.properties;

import java.util.HashMap;

public enum Attack {
    NONE(0),
    BASIC(1),
    POWERFUL(2),
    UNSTOPPABLE(3);

    final int power;
    static final HashMap<Integer, Attack> BY_POWER = new HashMap<>();
    Attack(int power) {
        this.power = power;
    }

    public int getPower() {
        return power;
    }

    public Attack getByValue(int value) {
        return BY_POWER.get(value);
    }

    static {
        for (Attack attack : values()) {
            BY_POWER.put(attack.getPower(), attack);
        }
    }
}
