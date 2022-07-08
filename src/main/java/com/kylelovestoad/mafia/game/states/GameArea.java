package com.kylelovestoad.mafia.game.states;

import com.kylelovestoad.mafia.GamePlayer;
import com.kylelovestoad.mafia.MafiaPlugin;
import com.kylelovestoad.mafia.game.roles.Role;
import com.kylelovestoad.mafia.manager.GameManager;
import com.kylelovestoad.mafia.manager.RoleManager;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GameArea {

    private List<Location> spawnLocations;
    private Location spectatorLocation;
    private final String name;

    private final List<Player> activePlayers = new ArrayList<>();
    private final List<Player> spectatorPlayers = new ArrayList<>();

    private final List<GamePlayer<? extends Role>> gamePlayers = new ArrayList<>();
    private final GameManager gameManager;

    private GameState gameState = new DefaultState();

    public GameArea(String name, GameManager gameManager) {
        this.name = name;
        this.gameManager = gameManager;
    }

    public void addPlayer(Player player) {
        activePlayers.add(player);
    }

    public void removePlayer(Player player) {
        activePlayers.remove(player);
    }

    public void addSpectator(Player player) {
        spectatorPlayers.add(player);
    }

    public void removeSpectator(Player player) {
        activePlayers.remove(player);
    }

    public boolean isPlaying(Player player) {
        return activePlayers.contains(player);
    }


    public boolean isSpectating(Player player) {
        return spectatorPlayers.contains(player);
    }

    public List<Player> getActivePlayers() {
        return activePlayers;
    }

    public void broadcastMessage(TextComponent message) {
        getActivePlayers().stream().filter(Objects::nonNull).forEach(player -> player.sendMessage(message));
    }

    public void broadcastSound(Sound sound) {
        getActivePlayers().stream().filter(Objects::nonNull).forEach(player -> player.playSound(sound));
    }

    public void setTimeForActivePlayers(int time) {
        getActivePlayers().stream().filter(Objects::nonNull).forEach(player -> player.setPlayerTime(time, false));
    }

    public void setGameState(GameState gameState) {

        final MafiaPlugin mafiaPlugin = gameManager.getPlugin();

        this.gameState.onDisable(mafiaPlugin);

        this.gameState = gameState;

        this.gameState.onEnable(mafiaPlugin);

    }
}
