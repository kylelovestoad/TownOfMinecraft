package com.kylelovestoad.mafia.game.roles;

import com.kylelovestoad.mafia.game.Game;
import com.kylelovestoad.mafia.game.gameplayers.neutral.ArsonistPlayer;
import com.kylelovestoad.mafia.game.gameplayers.GamePlayer;
import com.kylelovestoad.mafia.game.roles.properties.Attack;
import com.kylelovestoad.mafia.game.roles.properties.Defense;
import com.kylelovestoad.mafia.game.roles.properties.Faction;
import com.kylelovestoad.mafia.manager.GeneralManager;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.format.TextColor;

import java.util.UUID;

public class ArsonistRole implements Role {
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
    public Attack attack() {
        return Attack.UNSTOPPABLE;
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
        return new ArsonistPlayer(playerUUID, this, game, generalManager);
    }

}
