package com.kylelovestoad.mafia.manager;

import com.kylelovestoad.mafia.MafiaPlugin;

public class GeneralManager {

    private final MafiaPlugin plugin;

    private final GameManager gameManager;

    private final ConfigurationManager configurationManager;

    private final RoleManager roleManager;

    private final GuiManager guiManager;

    public GeneralManager(MafiaPlugin plugin) {
        this.plugin = plugin;
        this.gameManager = new GameManager(this);
        this.configurationManager = new ConfigurationManager(this);
        this.roleManager = new RoleManager(this);
        this.guiManager = new GuiManager(this);
    }
    

    public MafiaPlugin getPlugin() {
        return plugin;
    }

    public GameManager getGameManager() {
        return gameManager;
    }

    public ConfigurationManager getConfigurationManager() {
        return configurationManager;
    }

    public RoleManager getRoleManager() {
        return roleManager;
    }

    public GuiManager getGuiManager() {
        return guiManager;
    }
}
