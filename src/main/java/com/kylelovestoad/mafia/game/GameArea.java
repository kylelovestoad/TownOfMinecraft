package com.kylelovestoad.mafia.game;

import com.kylelovestoad.mafia.Mafia;
import com.kylelovestoad.mafia.manager.GameManager;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GameArea {

    private List<Location> spawnLocations;
    private Location spectatorLocation;

    private final String name;

    private final List<UUID> activePlayers = new ArrayList<>();
    private final List<UUID> spectatorPlayers = new ArrayList<>();

    private final GameManager gameManager;

    private GameState gameState = new DefaultState();



    public GameArea(String name, GameManager gameManager) {
        this.name = name;
        this.gameManager = gameManager;
    }

    public void addPlayer(Player player) {
        activePlayers.add(player.getUniqueId());
    }

    public void removePlayer(Player player) {
        activePlayers.remove(player.getUniqueId());
    }

    public void addSpectator(Player player) {
        spectatorPlayers.add(player.getUniqueId());
    }

    public void removeSpectator(Player player) {
        activePlayers.remove(player.getUniqueId());
    }

    public boolean isPlaying(Player player) {
        return activePlayers.contains(player.getUniqueId());
    }


    public boolean isSpectating(Player player) {
        return spectatorPlayers.contains(player.getUniqueId());
    }

    public List<UUID> getActivePlayers() {
        return activePlayers;
    }



    public void broadcastMessage(TextComponent message) {
        for (UUID playerUUID : this.getActivePlayers()) {
            Player player = Bukkit.getPlayer(playerUUID);
            if (player != null) {
                player.sendMessage(message);
            }
        }

    }

    public void broadcastSound(Sound sound) {
        for (UUID playerUUID : this.getActivePlayers()) {
            Player player = Bukkit.getPlayer(playerUUID);
            if (player != null) {
                player.playSound(sound);
            }
        }
    }



    public void setGameState(GameState gameState) {

        final Mafia mafia = gameManager.getPlugin();

        this.gameState.onDisable(mafia);

        this.gameState = gameState;

        this.gameState.onEnable(mafia);

    }
}
