package com.kylelovestoad.mafia.game.gameplayers.town;

import com.kylelovestoad.mafia.game.Game;
import com.kylelovestoad.mafia.game.gameplayers.GamePlayer;
import com.kylelovestoad.mafia.game.roles.Role;
import com.kylelovestoad.mafia.manager.GeneralManager;

import java.util.UUID;

public abstract class TownPlayer extends GamePlayer {
    /**
     * @param playerUUID     The {@link UUID} for the player this is
     * @param role           The {@link Role} that this has
     * @param game           The {@link Game} that this is in
     * @param generalManager The {@link GeneralManager} that this is using
     */
    public TownPlayer(UUID playerUUID, Role role, Game game, GeneralManager generalManager) {
        super(playerUUID, role, game, generalManager);
    }

    abstract TownAlignment getTownAlignment();

}
