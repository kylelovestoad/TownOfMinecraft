package com.kylelovestoad.mafia.game.states;

import com.kylelovestoad.mafia.MafiaPlugin;
import com.kylelovestoad.mafia.game.Game;
import com.kylelovestoad.mafia.game.tasks.GameStartingTask;
import com.kylelovestoad.mafia.manager.GeneralManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Does the countdown before beginning the {@link ActiveGameState}
 */
public class StartingGameState extends GameState {

    private final Game game;

    private final GeneralManager generalManager;

    private final GameStartingTask gameStartingTask;

    public StartingGameState(Game game, GeneralManager generalManager) {
        this.game = game;
        this.generalManager = generalManager;
        gameStartingTask = new GameStartingTask(generalManager, game, () ->
                game.setGameState(new ActiveGameState(game, generalManager)), 5);
    }

    public GameStartingTask getGameStartingTask() {
        return gameStartingTask;
    }

    @Override
    public void onEnable(MafiaPlugin mafiaPlugin) {
        super.onEnable(mafiaPlugin);
        gameStartingTask.runTaskTimer(mafiaPlugin, 0, 20);
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
