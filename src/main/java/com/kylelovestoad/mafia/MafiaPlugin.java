package com.kylelovestoad.mafia;

import com.kylelovestoad.mafia.commands.MafiaBaseCommand;
import com.kylelovestoad.mafia.manager.ConfigurationManager;
import com.kylelovestoad.mafia.manager.GameManager;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.Objects;

public final class MafiaPlugin extends JavaPlugin {
    private final GameManager gameManager;
    public MafiaPlugin() {
        this.gameManager = new GameManager(this);
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("Successfully Loaded!");

        Objects.requireNonNull(getCommand("mafia")).setExecutor(new MafiaBaseCommand(this.gameManager));

        ConfigurationManager configurationManager = gameManager.getConfigurationManager();

        configurationManager.generateMainConfig();
        configurationManager.saveConfig();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("Disabled");
    }
}
