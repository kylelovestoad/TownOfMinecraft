package com.kylelovestoad.mafia.game.roles;

import com.kylelovestoad.mafia.game.Game;
import com.kylelovestoad.mafia.game.gameplayers.GamePlayer;
import com.kylelovestoad.mafia.game.gameplayers.town.VigilantePlayer;
import com.kylelovestoad.mafia.game.roles.properties.Defense;
import com.kylelovestoad.mafia.game.roles.properties.Faction;
import com.kylelovestoad.mafia.game.roles.properties.Attack;
import com.kylelovestoad.mafia.manager.GeneralManager;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;

import java.util.UUID;

public class VigilanteRole implements Role {
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
        return new VigilantePlayer(playerUUID, this, game, generalManager);
    }
}
