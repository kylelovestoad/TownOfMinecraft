package com.kylelovestoad.mafia;

import com.kylelovestoad.mafia.commands.MafiaBaseCommand;
import com.kylelovestoad.mafia.manager.ConfigurationManager;
import com.kylelovestoad.mafia.manager.GeneralManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class MafiaPlugin extends JavaPlugin {
    private final GeneralManager generalManager;
    public MafiaPlugin() {
        this.generalManager = new GeneralManager(this);
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("Successfully Loaded!");

        Objects.requireNonNull(getCommand("mafia")).setExecutor(new MafiaBaseCommand(this.generalManager));

        ConfigurationManager configurationManager = generalManager.getConfigurationManager();

        configurationManager.generateMainConfig();
        configurationManager.saveConfig();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("Disabled");
    }
}
