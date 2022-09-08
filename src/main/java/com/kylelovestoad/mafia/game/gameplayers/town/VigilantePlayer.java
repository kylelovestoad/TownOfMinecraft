package com.kylelovestoad.mafia.game.gameplayers.town;

import com.kylelovestoad.mafia.game.Game;
import com.kylelovestoad.mafia.game.roles.VigilanteRole;
import com.kylelovestoad.mafia.manager.GeneralManager;

import java.util.UUID;

public class VigilantePlayer extends TownPlayer {

    public VigilantePlayer(UUID playerUUID, VigilanteRole role, Game game, GeneralManager generalManager) {
        super(playerUUID, role, game, generalManager);
    }


    @Override
    TownAlignment getTownAlignment() {
        return TownAlignment.KILLING;
    }
}
