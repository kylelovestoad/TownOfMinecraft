package com.kylelovestoad.mafia.game.roles;

import com.kylelovestoad.mafia.game.Game;
import com.kylelovestoad.mafia.game.gameplayers.neutral.ExecutionerPlayer;
import com.kylelovestoad.mafia.game.gameplayers.GamePlayer;
import com.kylelovestoad.mafia.game.roles.properties.Attack;
import com.kylelovestoad.mafia.game.roles.properties.Defense;
import com.kylelovestoad.mafia.game.roles.properties.Faction;
import com.kylelovestoad.mafia.manager.GeneralManager;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;

import java.util.UUID;

public class ExecutionerRole implements Role {

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
    public Attack attack() {
        return Attack.NONE;
    }
    @Override
    public Defense defense() {
        return Defense.BASIC;
    }
    @Override
    public boolean isUnique() {
        return false;
    }

    @Override
    public GamePlayer gamePlayerOf(UUID playerUUID, Game game, GeneralManager generalManager) {
        return new ExecutionerPlayer(playerUUID, this, game, generalManager);
    }

}
