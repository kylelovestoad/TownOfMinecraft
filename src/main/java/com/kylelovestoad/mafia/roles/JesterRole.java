package com.kylelovestoad.mafia.roles;

import com.kylelovestoad.mafia.roles.roleproperties.Attack;
import com.kylelovestoad.mafia.roles.roleproperties.Defense;
import com.kylelovestoad.mafia.roles.roleproperties.Faction;
import net.kyori.adventure.text.format.TextColor;

public class JesterRole implements Role {
    @Override
    public Faction faction() {
        return Faction.NEUTRAL;
    }

    @Override
    public String objective() {
        return "Get yourself voted out";
    }

    @Override
    public String name() {
        return "Jester";
    }

    @Override
    public TextColor color() {
        return TextColor.color(0xf7b3da);
    }

    @Override
    public String description() {
        return "Wants to get themselves voted out";
    }

    @Override
    public Attack attackPower() {
        return Attack.NONE;
    }

    @Override
    public Defense defensePower() {
        return Defense.NONE;
    }

}
