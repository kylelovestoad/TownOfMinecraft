package com.kylelovestoad.mafia.roles;

import com.kylelovestoad.mafia.roles.roleproperties.Attack;
import com.kylelovestoad.mafia.roles.roleproperties.Defense;
import com.kylelovestoad.mafia.roles.roleproperties.Faction;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;

public class MafiosoRole implements Role {
    @Override
    public Faction faction() {
        return Faction.MAFIA;
    }

    @Override
    public String objective() {
        return "Kill anyone who would oppose the mafia";
    }

    @Override
    public String name() {
        return "Mafioso";
    }

    @Override
    public TextColor color() {
        return TextColor.color(0xdd0000);
    }

    @Override
    public String description() {
        return "";
    }

    @Override
    public Attack attackPower() {
        return Attack.BASIC;
    }

    @Override
    public Defense defensePower() {
        return Defense.NONE;
    }
}
