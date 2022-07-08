package com.kylelovestoad.mafia;

import com.kylelovestoad.mafia.game.roles.Role;
import com.kylelovestoad.mafia.game.roles.roleproperties.Faction;
import com.kylelovestoad.mafia.game.states.GameArea;
import org.bukkit.entity.Player;

public class GamePlayer<T extends Role>
{

    private final T role;
    private final Player player;

    private boolean won;

    public GamePlayer(Player player, T role) {
        this.player = player;
        this.role = role;
        won = false;
    }

    public Player getPlayer() {
        return player;
    }

    public T getRole() {
        return role;
    }

    public boolean hasWon() {
        return won;
    }

    public void win() {
        won = true;
    }

    public boolean isFaction(Faction faction) {
        return role.faction().equals(faction);
    }

    public <T extends Role> boolean isRole(Class<T> roleClass) {
        return role.getClass().equals(roleClass);
    }
}
