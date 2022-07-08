package com.kylelovestoad.mafia;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class ConfigurationBundle {

    private final YamlConfiguration yamlConfiguration;
    private final File file;

    public ConfigurationBundle(YamlConfiguration yamlConfiguration, File file) {
        this.yamlConfiguration = yamlConfiguration;
        this.file = file;
    }

    public YamlConfiguration getYamlConfiguration() {
        return yamlConfiguration;
    }

    public File getFile() {
        return file;
    }
}
