package com.kylelovestoad.mafia.manager;

import com.kylelovestoad.mafia.game.Game;
import com.kylelovestoad.mafia.game.gameplayers.GamePlayer;
import com.kylelovestoad.mafia.game.roles.*;
import com.kylelovestoad.mafia.game.roles.properties.Faction;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Manages role assignment & other role related operations
 */
public class RoleManager {

    private final GeneralManager generalManager;

    private final List<Role> roles = new ArrayList<>();

    public RoleManager(GeneralManager generalManager) {
        this.generalManager = generalManager;
//        addRole(new VillagerRole());
//        addRole(new MafiosoRole());
//        addRole(new JesterRole());
//        addRole(new ExecutionerRole());
        addRole(new ArsonistRole());
    }

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

    public Role getRandomRole() {
        final Random random = new Random();
        return roles.get(random.nextInt(roles.size()));
    }


    /**
     * Get a random role based on the filtering of the all any game mode in Town of Salem
     * This method will be updated to support custom game modes in the future
     * @return The random role from a filtered list
     */
    public Role getRandomRoleFiltered(Game game) {

        ConfigurationManager configurationManager = generalManager.getConfigurationManager();

//        int maxPlayers = configurationManager.getMainConfig()
//                .getConfigurationSection("players")
//                .getInt("maxPlayers");

        int maxMafia = configurationManager.getConfig("general.yml")
                .getConfigurationSection("players")
                .getInt("maxMafia");


        boolean mafiaSlotsFilled = game.getMafiaPlayers().size() == maxMafia;

        List<Role> filteredRoles = roles;

        final Random random = new Random();

        // Disallow more than the maximum mafia members from being in the game
        if (mafiaSlotsFilled) {
            filteredRoles = filteredRoles.stream()
                    .filter(role -> !role.faction().equals(Faction.MAFIA))
                    .collect(Collectors.toList());
        }

        // Disallow getting multiple unique roles in one game
        for (GamePlayer gamePlayer : game.getGamePlayers()) {
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
