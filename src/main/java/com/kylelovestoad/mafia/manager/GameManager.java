package com.kylelovestoad.mafia.manager;

import com.kylelovestoad.mafia.Mafia;

public class GameManager {

    private final Mafia plugin;

    private final RoleManager roleManager;

    private final GameAreaManager gameAreaManager;

    public GameManager(Mafia plugin) {
        this.plugin = plugin;
        this.gameAreaManager = new GameAreaManager(this);
        this.roleManager = new RoleManager(this);
    }

    public Mafia getPlugin() {
        return plugin;
    }

    public GameAreaManager getGameAreaManager() {
        return gameAreaManager;
    }

    public RoleManager getRoleManager() {
        return roleManager;
    }
}
