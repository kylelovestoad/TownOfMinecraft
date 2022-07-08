package com.kylelovestoad.mafia.manager;

import com.kylelovestoad.mafia.MafiaPlugin;

public class GameManager {

    private final MafiaPlugin plugin;

    private final GameAreaManager gameAreaManager;

    private final ConfigurationManager configurationManager;

    private final RoleManager roleManager;

    public GameManager(MafiaPlugin plugin) {
        this.plugin = plugin;
        this.gameAreaManager = new GameAreaManager(this);
        this.configurationManager = new ConfigurationManager(this);
        this.roleManager = new RoleManager(this);
    }

    public MafiaPlugin getPlugin() {
        return plugin;
    }

    public GameAreaManager getGameAreaManager() {
        return gameAreaManager;
    }

    public ConfigurationManager getConfigurationManager() {
        return configurationManager;
    }

    public RoleManager getRoleManager() {
        return roleManager;
    }
}
