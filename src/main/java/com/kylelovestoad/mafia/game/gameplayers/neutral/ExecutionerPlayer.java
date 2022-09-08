package com.kylelovestoad.mafia.game.gameplayers.neutral;

import com.kylelovestoad.mafia.game.Game;
import com.kylelovestoad.mafia.game.gameplayers.GamePlayer;
import com.kylelovestoad.mafia.game.roles.ExecutionerRole;
import com.kylelovestoad.mafia.manager.GeneralManager;

import java.util.UUID;

public class ExecutionerPlayer extends NeutralPlayer {

    private GamePlayer target;

    public ExecutionerPlayer(UUID playerUUID, ExecutionerRole role, Game game, GeneralManager generalManager) {
        super(playerUUID, role, game, generalManager);
    }

    public void setTarget(GamePlayer target) {
        this.target = target;
    }

    public GamePlayer getTarget() {
        return target;
    }

    @Override
    NeutralAlignment getNeutralAlignment() {
        return NeutralAlignment.CHAOS;
    }
}
