package com.kylelovestoad.mafia.game;

import com.kylelovestoad.mafia.Mafia;
import com.kylelovestoad.mafia.game.tasks.GameStartingTask;
import com.kylelovestoad.mafia.manager.GameManager;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class StartingGameState extends GameState {

    private final GameArea gameArea;

    private final GameManager gameManager;

    public StartingGameState(GameArea gameArea, GameManager gameManager) {
        this.gameArea = gameArea;
        this.gameManager = gameManager;
    }

    @Override
    public void onEnable(Mafia mafia) {
        super.onEnable(mafia);
        GameStartingTask gameStartingTask =
                new GameStartingTask(gameArea, () -> gameArea.setGameState(new ActiveGameState(gameArea, gameManager)), 5);

        gameStartingTask.runTaskTimer(mafia, 0, 20);
    }

    private void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (gameArea.isPlaying(player)) {
            gameArea.removePlayer(player);
        }
    }

    private void onDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (gameArea.isPlaying(player)) {
                event.setCancelled(true);
            }
        }
    }

    private void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (gameArea.isPlaying(player)) {
            event.setCancelled(true);
        }
    }

    private void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (gameArea.isPlaying(player)) {
            event.setCancelled(true);
        }
    }

}
