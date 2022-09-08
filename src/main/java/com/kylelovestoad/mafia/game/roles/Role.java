package com.kylelovestoad.mafia.game.roles;

import com.kylelovestoad.mafia.game.Game;
import com.kylelovestoad.mafia.game.gameplayers.GamePlayer;
import com.kylelovestoad.mafia.game.roles.properties.Attack;
import com.kylelovestoad.mafia.game.roles.properties.Defense;
import com.kylelovestoad.mafia.game.roles.properties.Faction;
import com.kylelovestoad.mafia.manager.GeneralManager;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.format.TextColor;

import java.util.UUID;

/**
 * Role contains several general methods that hold constants for each role implementation.
 */
public interface Role {
    Faction faction();
    String objective();
    String name();
    TextColor color();
    Sound introSound();
    Attack attack();
    Defense defense();
    boolean isUnique();
    GamePlayer gamePlayerOf(UUID uuid, Game game, GeneralManager generalManager);

}
