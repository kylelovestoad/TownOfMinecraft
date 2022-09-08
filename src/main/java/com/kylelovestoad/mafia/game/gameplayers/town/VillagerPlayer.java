package com.kylelovestoad.mafia.game.gameplayers.town;

import com.kylelovestoad.mafia.game.Game;
import com.kylelovestoad.mafia.game.roles.VillagerRole;
import com.kylelovestoad.mafia.manager.GeneralManager;

import java.util.UUID;

public class VillagerPlayer extends TownPlayer {

    public VillagerPlayer(UUID playerUUID, VillagerRole role, Game game, GeneralManager generalManager) {
        super(playerUUID, role, game, generalManager);
    }

    @Override
    TownAlignment getTownAlignment() {
        return TownAlignment.SUPPORT;
    }
}
