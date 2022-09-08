package com.kylelovestoad.mafia.game.gameplayers.mafia;

import com.kylelovestoad.mafia.game.Game;
import com.kylelovestoad.mafia.game.gameplayers.GamePlayer;
import com.kylelovestoad.mafia.game.roles.MafiosoRole;
import com.kylelovestoad.mafia.game.roles.Role;
import com.kylelovestoad.mafia.manager.GeneralManager;
import org.bukkit.entity.Player;

import java.util.UUID;

public class MafiosoPlayer extends GamePlayer {

    public MafiosoPlayer(UUID playerUUID, MafiosoRole role, Game game, GeneralManager generalManager) {
        super(playerUUID, role, game, generalManager);
    }
}
