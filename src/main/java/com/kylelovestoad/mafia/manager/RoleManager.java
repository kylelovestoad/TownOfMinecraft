package com.kylelovestoad.mafia.manager;

import com.kylelovestoad.mafia.GamePlayer;
import com.kylelovestoad.mafia.game.roles.*;
import com.kylelovestoad.mafia.game.roles.roleproperties.Faction;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class RoleManager {

    private final GameManager gameManager;

    private final List<Role> roles = new ArrayList<>();

    private final List<GamePlayer<?>> gamePlayers = new ArrayList<>();

    // Manages role assignment & other role related operations
    public RoleManager(GameManager gameManager) {
        this.gameManager = gameManager;
        addRole(new VillagerRole());
        addRole(new MafiosoRole());
        addRole(new JesterRole());
        addRole(new ExecutionerRole());
        addRole(new ArsonistRole());
    }

    //FIXME MULTIPLE GAMES WONT BE ABLE TO RUN BECAUSE THERE IS ONLY ONE INSTANCE OF ROLEMANAGER


    public void addRole(Role role) {

        boolean duplicateName = this.roles.stream().anyMatch(r -> r.equals(role));

        if (duplicateName) {
            throw new IllegalArgumentException("Duplicate role found");
        }

        roles.add(role);
    }

    public List<Role> getRoles() {
        return roles;
    }

    public <T extends Role> List<GamePlayer<T>> getPlayersFromRole(Class<T> roleClass) throws ClassCastException {
        //noinspection unchecked
        return gamePlayers.stream()
                        .filter(gamePlayer -> gamePlayer.isRole(roleClass))
                        .map(gamePlayer -> (GamePlayer<T>) gamePlayer)
                        .collect(Collectors.toList());
    }

    public void assignRole(Player player, Role role) {
        gamePlayers.add(new GamePlayer<>(player, role));
    }

    public List<GamePlayer<? extends Role>> getGamePlayers() {
        return gamePlayers;
    }

    public List<GamePlayer<? extends Role>> getTownPlayers() {
        return gamePlayers.stream()
                .filter(gamePlayer -> gamePlayer.isFaction(Faction.TOWN))
                .collect(Collectors.toList());
    }

    public List<GamePlayer<? extends Role>> getMafiaPlayers() {
        return gamePlayers.stream()
                .filter(gamePlayer -> gamePlayer.isFaction(Faction.MAFIA))
                .collect(Collectors.toList());
    }

    public Role getRandomRole() {
        final Random random = new Random();
        return roles.get(random.nextInt(roles.size()));
    }


    /**
     * Get a random role based on the filtering of the all any game mode in Town of Salem
     * This method will be updated to support custom game modes in the future
     * @return The random role from a filtered list
     */
    public Role getRandomRoleFiltered() {

        ConfigurationManager configurationManager = gameManager.getConfigurationManager();

//        int maxPlayers = configurationManager.getMainConfig()
//                .getConfigurationSection("players")
//                .getInt("maxPlayers");

        int maxMafia = configurationManager.getMainConfig()
                .getConfigurationSection("players")
                .getInt("maxMafia");

        boolean mafiaSlotsFilled = getMafiaPlayers().size() == maxMafia;

        List<Role> filteredRoles = roles;

        final Random random = new Random();

        // Disallow more than the maximum mafia members from being in the game
        if (mafiaSlotsFilled) {
            filteredRoles = filteredRoles.stream()
                    .filter(role -> !role.faction().equals(Faction.MAFIA))
                    .collect(Collectors.toList());
        }

        // Disallow getting multiple unique roles in one game
        for (GamePlayer<?> gamePlayer : gamePlayers) {
            Role gamePlayerRole = gamePlayer.getRole();
            if (gamePlayerRole.isUnique()) {
                filteredRoles = filteredRoles.stream()
                        .filter(role -> !role.equals(gamePlayerRole))
                        .collect(Collectors.toList());
            }
        }

        return filteredRoles.get(random.nextInt(filteredRoles.size()));
    }
}
