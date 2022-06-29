package com.kylelovestoad.mafia.roles;

import com.kylelovestoad.mafia.roles.roleproperties.Attack;
import com.kylelovestoad.mafia.roles.roleproperties.Defense;
import com.kylelovestoad.mafia.roles.roleproperties.Faction;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;

public class VillagerRole implements Role {
    @Override
    public Faction faction() {
        return Faction.TOWN;
    }

    @Override
    public String objective() {
        return "Vote out all of the mafia members";
    }

    @Override
    public String name() {
        return "Villager";
    }

    @Override
    public TextColor color() {
        return NamedTextColor.GREEN;
    }

    @Override
    public String description() {
        return "Town member with no special abilities";
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
