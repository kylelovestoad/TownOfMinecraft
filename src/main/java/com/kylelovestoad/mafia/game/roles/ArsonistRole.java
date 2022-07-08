package com.kylelovestoad.mafia.game.roles;

import com.kylelovestoad.mafia.game.roles.roleproperties.Attack;
import com.kylelovestoad.mafia.game.roles.roleproperties.Defense;
import com.kylelovestoad.mafia.game.roles.roleproperties.Faction;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.format.TextColor;

public class ArsonistRole extends Role {

    @Override
    public Faction faction() {
        return Faction.NEUTRAL;
    }

    @Override
    public String objective() {
        return "Live to see everyone burn";
    }

    @Override
    public String name() {
        return "Arsonist";
    }

    @Override
    public TextColor color() {
        return TextColor.color(0xee7600);
    }

    @Override
    public Sound introSound() {
        return Sound.sound(Key.key("entity.blaze.shoot"), Sound.Source.BLOCK, 1, 1);
    }

    @Override
    public Attack attackPower() {
        return Attack.UNSTOPPABLE;
    }

    @Override
    public Defense defensePower() {
        return Defense.BASIC;
    }

    @Override
    public boolean isUnique() {
        return false;
    }

}
