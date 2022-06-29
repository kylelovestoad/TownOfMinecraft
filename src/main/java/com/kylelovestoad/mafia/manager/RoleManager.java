package com.kylelovestoad.mafia.manager;

import com.kylelovestoad.mafia.MafiaPlayer;
import com.kylelovestoad.mafia.roles.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class RoleManager {

    private final GameManager gameManager;

    List<Role> roles = new ArrayList<>();

    List<MafiaPlayer> mafiaPlayers = new ArrayList<>();

    // Manages role assignment & other role related operations
    public RoleManager(GameManager gameManager) {
        this.gameManager = gameManager;
        addRole(new VillagerRole());
        addRole(new MafiosoRole());
        addRole(new JesterRole());
        addRole(new ExecutionerRole());
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

    public void createMafiaPlayer(Player player, Role role) {
        mafiaPlayers.add(new MafiaPlayer(player, role));
    }
}
