package com.kylelovestoad.mafia.game.roles;

import com.kylelovestoad.mafia.game.Game;
import com.kylelovestoad.mafia.game.gameplayers.GamePlayer;
import com.kylelovestoad.mafia.game.gameplayers.mafia.MafiosoPlayer;
import com.kylelovestoad.mafia.game.roles.properties.Defense;
import com.kylelovestoad.mafia.game.roles.properties.Faction;
import com.kylelovestoad.mafia.game.roles.properties.Attack;
import com.kylelovestoad.mafia.manager.GeneralManager;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.format.TextColor;

import java.util.UUID;

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
    public Sound introSound() {
        return Sound.sound(Key.key("entity.ender_dragon.growl"), Sound.Source.BLOCK, 0.5f, 1);
    }

    @Override
    public Attack attack() {
        return Attack.BASIC;
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
        return new MafiosoPlayer(playerUUID, this, game, generalManager);
    }
}
