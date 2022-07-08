package com.kylelovestoad.mafia.game.roles;

import com.kylelovestoad.mafia.game.roles.roleproperties.Defense;
import com.kylelovestoad.mafia.game.roles.roleproperties.Faction;
import com.kylelovestoad.mafia.game.roles.roleproperties.Attack;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;

public class VigilanteRole extends Role {
    @Override
    public Faction faction() {
        return Faction.TOWN;
    }

    @Override
    public String objective() {
        return "Take justice into your own hands";
    }

    @Override
    public String name() {
        return "Vigilante";
    }

    @Override
    public TextColor color() {
        return NamedTextColor.GREEN;
    }

    @Override
    public Sound introSound() {
        return Sound.sound(Key.key("block.dispenser.fire"), Sound.Source.BLOCK, 1, 1);
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
