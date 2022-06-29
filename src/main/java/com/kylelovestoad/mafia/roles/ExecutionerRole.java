package com.kylelovestoad.mafia.roles;

import com.kylelovestoad.mafia.MafiaPlayer;
import com.kylelovestoad.mafia.roles.roleproperties.Attack;
import com.kylelovestoad.mafia.roles.roleproperties.Defense;
import com.kylelovestoad.mafia.roles.roleproperties.Faction;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;

public class ExecutionerRole implements Role {

    private MafiaPlayer target = null;

    @Override
    public Faction faction() {
        return Faction.NEUTRAL;
    }

    @Override
    public String objective() {
        return "Test";
    }

    @Override
    public String name() {
        return "Executioner";
    }

    @Override
    public TextColor color() {
        return NamedTextColor.GRAY;
    }

    @Override
    public String description() {
        return "test";
    }

    @Override
    public Attack attackPower() {
        return Attack.NONE;
    }

    @Override
    public Defense defensePower() {
        return Defense.BASIC;
    }

    public void setTarget(MafiaPlayer mafiaPlayer) {
        target = mafiaPlayer;
    }

    public MafiaPlayer getTarget() {
        return target;
    }
}
