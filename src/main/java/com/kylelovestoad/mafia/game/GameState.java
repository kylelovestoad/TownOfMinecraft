package com.kylelovestoad.mafia.game;

import com.kylelovestoad.mafia.Mafia;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

/**
 * Class for separating the game into several states
 */
public abstract class GameState implements Listener {

    /**
     * Executes when the game state starts
     * @param mafia The plugin object
     */
    public void onEnable(Mafia mafia) {
        mafia.getServer().getPluginManager().registerEvents(this, mafia);
    }

    /**
     * Executes when the game state ends
     * @param mafia The plugin object
     */
    public void onDisable(Mafia mafia) {
        HandlerList.unregisterAll(this);
    }

}
