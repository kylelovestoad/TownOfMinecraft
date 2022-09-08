package com.kylelovestoad.mafia.game;

import com.kylelovestoad.mafia.MafiaPlugin;
import com.kylelovestoad.mafia.game.gameplayers.GamePlayer;
import com.kylelovestoad.mafia.game.gameplayers.properties.Status;
import com.kylelovestoad.mafia.game.roles.properties.Faction;
import com.kylelovestoad.mafia.game.states.DefaultState;
import com.kylelovestoad.mafia.game.states.GameState;
import com.kylelovestoad.mafia.game.states.LobbyGameState;
import com.kylelovestoad.mafia.game.states.StartingGameState;
import com.kylelovestoad.mafia.manager.GeneralManager;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Represents a mafia game
 */
public class Game {

    private List<Location> spawnLocations;
    private Location spectatorLocation;
    private final String name;
    private final List<UUID> activePlayers = new ArrayList<>();
    private final List<UUID> spectatorPlayers = new ArrayList<>();

    private final List<GamePlayer> gamePlayers = new ArrayList<>();
//    private final List<GamePlayer> preGraveyardPlayers = new ArrayList<>();
//    private final List<GamePlayer> graveyardPlayers = new ArrayList<>();
    private final GeneralManager generalManager;

    private GameState gameState = new DefaultState();

    public Game(String name, GeneralManager generalManager) {
        this.name = name;
        this.generalManager = generalManager;
    }

    public void addPlayer(Player player) {
        activePlayers.add(player.getUniqueId());
        int minPlayers = generalManager.getConfigurationManager().getConfig("general.yml").getInt("");
        if (activePlayers.size() >= minPlayers && !(gameState instanceof StartingGameState)) {
            setGameState(new StartingGameState(this, generalManager));
        }
    }

    public void removePlayer(Player player) {
        activePlayers.remove(player.getUniqueId());
        int minPlayers = generalManager.getConfigurationManager().getConfig("general.yml").getInt("");

        // Need this check in order to make sure that a game doesn't start without the minimum number of players
        if (activePlayers.size() < minPlayers && gameState instanceof StartingGameState) {
            setGameState(new LobbyGameState(this, generalManager));
            broadcastMessage(Component.text("Cancelling start, not enough players!", NamedTextColor.RED));
        }
    }

    public List<UUID> getActivePlayers() {
        return activePlayers;
    }

    public void addGamePlayer(GamePlayer gamePlayer) {
        gamePlayers.add(gamePlayer);
    }

    public void removeGamePlayer(GamePlayer gamePlayer) {
        gamePlayers.remove(gamePlayer);
    }

    public List<GamePlayer> getGamePlayers() {
        return gamePlayers;
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
    public List<GamePlayer> getQueuedGamePlayers() {
        return gamePlayers.stream()
                .filter(gamePlayer -> gamePlayer.getStatus().equals(Status.QUEUED))
                .collect(Collectors.toList());
    }

    public List<GamePlayer> getGraveyardGamePlayers() {
        return gamePlayers.stream()
                .filter(gamePlayer -> gamePlayer.getStatus().equals(Status.DEAD))
                .collect(Collectors.toList());
    }


    public List<GamePlayer> getTownPlayers() {
        return gamePlayers.stream()
                .filter(gamePlayer -> gamePlayer.isFaction(Faction.TOWN))
                .collect(Collectors.toList());
    }

    public List<GamePlayer> getMafiaPlayers() {
        return gamePlayers.stream()
                .filter(gamePlayer -> gamePlayer.isFaction(Faction.MAFIA))
                .collect(Collectors.toList());
    }

    public <T extends GamePlayer> List<T> getPlayersFromRole(Class<T> gamePlayerClass) {
        return gamePlayers.stream()
                .filter(gamePlayerClass::isInstance)
                .map(gamePlayerClass::cast)
                .collect(Collectors.toList());
    }

    public void broadcastMessage(TextComponent message) {
        getNonNullPlayerStream().forEach(player -> player.sendMessage(message));
    }

    public void broadcastSound(Sound sound) {
        getNonNullPlayerStream().forEach(player -> player.playSound(sound));
    }

    public void broadcastActionBar(TextComponent message) {
        getNonNullPlayerStream().forEach(player -> player.sendActionBar(message));

    }

    public void setTimeForActivePlayers(int time) {
        getNonNullPlayerStream().forEach(player -> player.setPlayerTime(time, false));
    }

    public void sendMessage(TextComponent message, GamePlayer gamePlayer) {
        gamePlayer.getPlayer().sendMessage(message);
    }

    public void showTitle(TextComponent title, GamePlayer gamePlayer) {
        gamePlayer.getPlayer().showTitle(Title.title(title, Component.empty()));
    }
    public void showTitle(TextComponent title, TextComponent subtitle, GamePlayer gamePlayer) {
        gamePlayer.getPlayer().showTitle(Title.title(title, subtitle));
    }

    public void setGameState(GameState gameState) {

        final MafiaPlugin mafiaPlugin = generalManager.getPlugin();

        this.gameState.onDisable(mafiaPlugin);

        this.gameState = gameState;

        this.gameState.onEnable(mafiaPlugin);

    }

    public GamePlayer getGamePlayerFromUUID(UUID uuid) {
        return getGamePlayers().stream()
                .filter(gamePlayer -> gamePlayer.getPlayer().getUniqueId().equals(uuid))
                .findFirst()
                .orElse(null);
    }

    /**
     *
     * @return A stream with {@link UUID UUIDs} mapped to non null {@link Player Players}
     */
    private Stream<Player> getNonNullPlayerStream() {
        return getActivePlayers().stream().map(Bukkit::getPlayer).filter(Objects::nonNull);
    }

}
