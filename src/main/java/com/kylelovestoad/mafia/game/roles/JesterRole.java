package com.kylelovestoad.mafia.game.roles;

import com.kylelovestoad.mafia.game.Game;
import com.kylelovestoad.mafia.game.gameplayers.GamePlayer;
import com.kylelovestoad.mafia.game.gameplayers.neutral.JesterPlayer;
import com.kylelovestoad.mafia.game.roles.properties.Attack;
import com.kylelovestoad.mafia.game.roles.properties.Defense;
import com.kylelovestoad.mafia.game.roles.properties.Faction;
import com.kylelovestoad.mafia.manager.GeneralManager;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.format.TextColor;

import java.util.UUID;

public class JesterRole implements Role {
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
    public Attack attack() {
        return Attack.NONE;
    }

    @Override
    public Defense defense() {
        return Defense.NONE;
    }

    @Override
    public boolean isUnique() {
        return false;
    }

    @Override
    public GamePlayer gamePlayerOf(UUID playerUUID, Game game, GeneralManager generalManager) {
        return new JesterPlayer(playerUUID, this, game, generalManager);
    }

}
