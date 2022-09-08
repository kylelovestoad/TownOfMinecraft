package com.kylelovestoad.mafia.game.states;

import com.kylelovestoad.mafia.game.Game;
import com.kylelovestoad.mafia.manager.GeneralManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * State used before the game has started. Waits for players to join and then begins the {@link StartingGameState}
 */
public class LobbyGameState extends GameState {

    private final Game game;
    private final GeneralManager generalManager;

    public LobbyGameState(Game game, GeneralManager generalManager) {
        this.game = game;
        this.generalManager = generalManager;
    }

    @EventHandler
    private void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (game.isPlaying(player)) {
            game.removePlayer(player);
        }
    }

    @EventHandler
    private void onDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (game.isPlaying(player)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    private void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (game.isPlaying(player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    private void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (game.isPlaying(player)) {
            event.setCancelled(true);
        }
    }

}
