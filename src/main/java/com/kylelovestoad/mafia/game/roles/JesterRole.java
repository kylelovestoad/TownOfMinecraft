package com.kylelovestoad.mafia.game.roles;

import com.kylelovestoad.mafia.game.roles.roleproperties.Attack;
import com.kylelovestoad.mafia.game.roles.roleproperties.Defense;
import com.kylelovestoad.mafia.game.roles.roleproperties.Faction;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.format.TextColor;

public class JesterRole extends Role {
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
    public Sound introSound() {
        return Sound.sound(Key.key("block.bell.use"), Sound.Source.BLOCK, 1, 1);
    }

    @Override
    public Attack attackPower() {
        return Attack.NONE;
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
