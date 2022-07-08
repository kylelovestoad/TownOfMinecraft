package com.kylelovestoad.mafia.game.roles;

import com.kylelovestoad.mafia.game.roles.roleproperties.Attack;
import com.kylelovestoad.mafia.game.roles.roleproperties.Defense;
import com.kylelovestoad.mafia.game.roles.roleproperties.Faction;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;

public class VillagerRole extends Role {
    @Override
    public Faction faction() {
        return Faction.TOWN;
    }

    @Override
    public String objective() {
        return "Vote out every criminal and evildoer";
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
    public Sound introSound() {
        return Sound.sound(Key.key("entity.villager.yes"), Sound.Source.BLOCK, 1, 1);
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
