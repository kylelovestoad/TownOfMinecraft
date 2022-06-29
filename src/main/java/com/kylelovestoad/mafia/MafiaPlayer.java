package com.kylelovestoad.mafia;

import com.kylelovestoad.mafia.roles.Role;
import org.bukkit.entity.Player;

public class MafiaPlayer {

    private final Role role;
    private final Player player;

    private boolean won;

    public MafiaPlayer(Player player, Role role) {
        this.player = player;
        this.role = role;
        won = false;
    }

    public Player getPlayer() {
        return player;
    }

    public Role getRole() {
        return role;
    }

    public boolean hasWon() {
        return won;
    }

    public void win() {
        won = true;
    }

}
