package com.kylelovestoad.mafia.game.gameplayers.properties;

import com.kylelovestoad.mafia.game.gameplayers.GamePlayer;

public class NamedAbility implements Ability {


    private final String name;
    private final Runnable runnable;


    public NamedAbility(String name, Runnable runnable) {
        this.name = name;
        this.runnable = runnable;
    }


    public String name() {
        return name;
    }

    @Override
    public void useAbility() {
        runnable.run();
    }
}
