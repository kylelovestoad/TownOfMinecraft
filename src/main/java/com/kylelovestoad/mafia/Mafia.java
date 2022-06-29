package com.kylelovestoad.mafia;

import com.kylelovestoad.mafia.commands.MafiaBaseCommand;
import com.kylelovestoad.mafia.manager.GameManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.logging.Logger;

public final class Mafia extends JavaPlugin {

    private final Logger LOGGER = Bukkit.getLogger();

    private final GameManager gameManager;

    public Mafia() {
        this.gameManager = new GameManager(this);
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        LOGGER.info("Successfully Loaded!");

        Objects.requireNonNull(getCommand("mafia")).setExecutor(new MafiaBaseCommand(this.gameManager));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        LOGGER.info("Disabled");
    }


}
