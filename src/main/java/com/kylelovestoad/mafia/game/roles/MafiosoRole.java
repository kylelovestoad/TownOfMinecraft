package com.kylelovestoad.mafia.game.roles;

import com.kylelovestoad.mafia.game.roles.roleproperties.Defense;
import com.kylelovestoad.mafia.game.roles.roleproperties.Faction;
import com.kylelovestoad.mafia.game.roles.roleproperties.Attack;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.format.TextColor;

public class MafiosoRole extends Role {
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
    public Sound introSound() {
        return Sound.sound(Key.key("entity.ender_dragon.growl"), Sound.Source.BLOCK, 0.5f, 1);
    }

    @Override
    public Attack attackPower() {
        return Attack.BASIC;
    }

    @Override
    public Defense defensePower() {
        return Defense.NONE;
    }

    @Override
    public boolean isUnique() {
        return false;
    }
}
