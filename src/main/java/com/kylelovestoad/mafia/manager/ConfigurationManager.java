package com.kylelovestoad.mafia.manager;

import com.kylelovestoad.mafia.MafiaPlugin;
import com.kylelovestoad.mafia.game.roles.Role;
import com.kylelovestoad.mafia.ConfigurationBundle;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class ConfigurationManager {


    private final GameManager gameManager;

    private final YamlConfiguration mainConfig;
    private final YamlConfiguration gameAreaConfig;

    private final YamlConfiguration gameModeConfig;
    private final File mainConfigFile;
    private final File gameAreaConfigFile;
    private final File gameModeConfigFile;

    public ConfigurationManager(GameManager gameManager) {
        this.gameManager = gameManager;

        ConfigurationBundle generalBundle = createConfig("general.yml");
        ConfigurationBundle gameAreaBundle = createConfig("maps.yml");
        ConfigurationBundle gameModeBundle = createConfig("gamemodes.yml");

        mainConfig = generalBundle.getYamlConfiguration();
        mainConfigFile = generalBundle.getFile();

        gameAreaConfig = gameAreaBundle.getYamlConfiguration();
        gameAreaConfigFile = gameAreaBundle.getFile();

        gameModeConfig = gameModeBundle.getYamlConfiguration();
        gameModeConfigFile = gameModeBundle.getFile();
    }

    public ConfigurationBundle createConfig(String filename) {
        MafiaPlugin mafiaPlugin = gameManager.getPlugin();
        File dataFolder = mafiaPlugin.getDataFolder();
        Logger logger = mafiaPlugin.getLogger();

        if (!dataFolder.exists()) {
            dataFolder.mkdir();
            logger.info("Successfully created new data folder");
        }

        File configFile = new File(dataFolder, filename);

        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
                logger.info("Successfully created new config file '" + filename + "'");
            } catch (IOException e) {
                logger.severe("Config file '" + filename + "' was not able to be created!");
                throw new RuntimeException(e);
            }
        }

        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(configFile);

        return new ConfigurationBundle(yamlConfiguration, configFile);

    }

    public void saveConfig() {
        try {
            mainConfig.save(mainConfigFile);
            gameAreaConfig.save(gameAreaConfigFile);
            gameModeConfig.save(gameModeConfigFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void generateMainConfig() {

        // So that user defined config doesn't get reset to default on restart or reload
        if (mainConfigFile.exists()) {
            return;
        }

        ConfigurationSection playersSection = mainConfig.createSection("players");
        playersSection.set("minPlayers", 1);
        playersSection.set("maxPlayers", 15);
        playersSection.set("maxMafia", 4);

        mainConfig.createSection("roles");
        gameManager.getRoleManager().getRoles().forEach(this::saveRole);

    }

    public void generateGameModeConfig() {

        if (gameModeConfigFile.exists()) {
            return;
        }

    }

    public void saveRole(Role role) {
        String roleNameLower = role.name().toLowerCase();
        if (mainConfig.isConfigurationSection(roleNameLower)) {
            mainConfig.set(roleNameLower, null);
        }

        ConfigurationSection rolesSection = mainConfig.getConfigurationSection("roles");
        ConfigurationSection subSection = rolesSection.createSection(roleNameLower);
        subSection.set("disabled", false);
    }

    public void saveGameMode() {

    }

    public void writeLocation(Location location, ConfigurationSection section) {
        section.set("worldName", location.getWorld().getName());
        section.set("x", location.getX());
        section.set("y", location.getY());
        section.set("z", location.getZ());
        section.set("yaw", location.getYaw());
        section.set("pitch", location.getPitch());
    }

    public Location getLocationFrom(ConfigurationSection section) {
        String worldName = section.getString("worldName");

        if (worldName == null) {
            return null;
        }

        World world = Bukkit.getWorld(worldName);

        return new Location(
                world,
                section.getDouble("x"),
                section.getDouble("y"),
                section.getDouble("z"),
                (float) section.getDouble("yaw"),
                (float) section.getDouble("pitch")
        );
    }

    public YamlConfiguration getMainConfig() {
        return mainConfig;
    }

    public YamlConfiguration getGameAreaConfig() {
        return gameAreaConfig;
    }
}
