package com.kylelovestoad.mafia.game.roles;

import com.kylelovestoad.mafia.GamePlayer;
import com.kylelovestoad.mafia.game.roles.roleproperties.Attack;
import com.kylelovestoad.mafia.game.roles.roleproperties.Defense;
import com.kylelovestoad.mafia.game.roles.roleproperties.Faction;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;

public class ExecutionerRole extends Role {

    private GamePlayer<?> target = null;

    @Override
    public Faction faction() {
        return Faction.NEUTRAL;
    }
    @Override
    public String objective() {
        return "Get your target voted out";
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
    public Sound introSound() {
        return Sound.sound(Key.key("entity.blaze.hurt"), Sound.Source.BLOCK, 1, 1);
    }
    @Override
    public Attack attackPower() {
        return Attack.NONE;
    }
    @Override
    public Defense defensePower() {
        return Defense.BASIC;
    }
    @Override
    public boolean isUnique() {
        return false;
    }

    public GamePlayer<?> getTarget() {
        return target;
    }

    public void setTarget(GamePlayer<?> target) {
        this.target = target;
    }
}
