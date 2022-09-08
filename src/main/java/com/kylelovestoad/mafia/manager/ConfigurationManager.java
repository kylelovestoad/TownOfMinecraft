package com.kylelovestoad.mafia.manager;

import com.kylelovestoad.mafia.MafiaPlugin;
import com.kylelovestoad.mafia.game.roles.Role;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Class used to interact with configuration
 */
public class ConfigurationManager {


    private final GeneralManager generalManager;

    private final List<ConfigurationBundle> configurationBundles = new ArrayList<>();

    public ConfigurationManager(GeneralManager generalManager) {
        this.generalManager = generalManager;

        createConfig("general.yml");
        createConfig("maps.yml");
        createConfig("gamemodes.yml");

    }

    public void createConfig(String filename) {
        MafiaPlugin mafiaPlugin = generalManager.getPlugin();
        File dataFolder = mafiaPlugin.getDataFolder();
        Logger logger = mafiaPlugin.getLogger();

        if (!dataFolder.exists()) {
            dataFolder.mkdir();
            logger.info("Successfully created new data folder");
        }

        File configFile = new File(dataFolder, filename);

        configurationBundles.add(new ConfigurationBundle(configFile, new YamlConfiguration()));

        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
                logger.info("Successfully created new config file '" + filename + "'");
            } catch (IOException e) {
                logger.severe("Config file '" + filename + "' was not able to be created!");
                throw new RuntimeException(e);
            }
        }
    }

    public void saveConfig() {
        configurationBundles.forEach(configurationBundle -> {
            try {
                // TODO uncomment this later to prevent user's config from being reset after restart
//                if (configurationBundle.getFile().exists() && configurationBundle.getFile().length() != 0) {
//                    return;
//                }
                configurationBundle.getYamlConfiguration().save(configurationBundle.getFile());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void generateMainConfig() {

        File mainConfigFile = getConfigFile("general.yml");
        YamlConfiguration mainConfig = getConfig("general.yml");

        generalManager.getPlugin().getLogger().info(String.valueOf(mainConfigFile.length()));



        ConfigurationSection generalSection = mainConfig.createSection("general");
        generalSection.set("maxTrials", 3);

        ConfigurationSection playersSection = mainConfig.createSection("players");
        playersSection.set("minPlayers", 1);
        playersSection.set("maxPlayers", 15);
        playersSection.set("maxMafia", 4);

        ConfigurationSection itemsSection = mainConfig.createSection("item_names");
        itemsSection.set("alive_players_checker", "Alive Players");
        itemsSection.set("graveyard_players_checker", "Graveyard Players");
        itemsSection.set("arsonist_ability", "Douse");

        mainConfig.createSection("roles");
        generalManager.getRoleManager().getRoles().forEach(this::saveRole);

    }

    public void generateGameModeConfig() {

        File gameModeConfigFile = getConfigFile("gamemodes.yml");

        //TODO maybe
        if (gameModeConfigFile.exists()) {
            return;
        }

    }

    public void saveRole(Role role) {
        YamlConfiguration mainConfig = getConfig("general.yml");


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

    private ConfigurationBundle getConfigurationBundle(String filename) {
        return configurationBundles.stream()
                .filter(configurationBundle -> configurationBundle.getFile().getName().equals(filename))
                .findFirst()
                .orElse(null);
    }

    public File getConfigFile(String filename) {
        return getConfigurationBundle(filename).getFile();
    }

    public YamlConfiguration getConfig(String filename) {
        return getConfigurationBundle(filename).getYamlConfiguration();
    }

    private class ConfigurationBundle {

        private final File file;
        private final YamlConfiguration yamlConfiguration;

        public ConfigurationBundle(File file, YamlConfiguration yamlConfiguration) {
            this.file = file;
            this.yamlConfiguration = yamlConfiguration;
        }

        public File getFile() {
            return file;
        }

        public YamlConfiguration getYamlConfiguration() {
            return yamlConfiguration;
        }
    }
}
