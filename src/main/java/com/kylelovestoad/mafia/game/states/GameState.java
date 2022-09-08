package com.kylelovestoad.mafia.game.states;

import com.kylelovestoad.mafia.MafiaPlugin;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

/**
 * Class for separating the game into several states
 */
public abstract class GameState implements Listener {

    /**
     * Executes when the game state starts
     * @param mafiaPlugin The plugin object
     */
    public void onEnable(MafiaPlugin mafiaPlugin) {
        mafiaPlugin.getServer().getPluginManager().registerEvents(this, mafiaPlugin);
    }

    /**
     * Executes when the game state ends
     * @param mafiaPlugin The plugin object
     */
    public void onDisable(MafiaPlugin mafiaPlugin) {
        HandlerList.unregisterAll(this);
    }
}
