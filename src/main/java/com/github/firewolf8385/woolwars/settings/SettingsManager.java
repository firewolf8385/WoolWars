package com.github.firewolf8385.woolwars.settings;

import com.github.firewolf8385.woolwars.WoolWars;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

/**
 * Allows easy access to plugin configuration
 * files. Stores spawn and arena locations.
 */
public class SettingsManager {
    private final FileConfiguration arenas;
    private final File arenasFile;
    private FileConfiguration config;
    private final File configFile;
    private final FileConfiguration kits;
    private final File kitsFile;
    private final FileConfiguration levels;
    private final File levalsFile;
    private final FileConfiguration messages;
    private final File messagesFile;

    public SettingsManager(WoolWars plugin) {
        config = plugin.getConfig();
        config.options().copyDefaults(true);
        configFile = new File(plugin.getDataFolder(), "config.yml");
        plugin.saveConfig();

        arenasFile = new File(plugin.getDataFolder(), "arenas.yml");
        arenas = YamlConfiguration.loadConfiguration(arenasFile);
        if(!arenasFile.exists()) {
            plugin.saveResource("arenas.yml", false);
        }

        kitsFile = new File(plugin.getDataFolder(), "kits.yml");
        kits = YamlConfiguration.loadConfiguration(kitsFile);
        if(!kitsFile.exists()) {
            plugin.saveResource("kits.yml", false);
        }

        levalsFile = new File(plugin.getDataFolder(), "levels.yml");
        levels = YamlConfiguration.loadConfiguration(levalsFile);
        if(!levalsFile.exists()) {
            plugin.saveResource("levels.yml", false);
        }

        messagesFile = new File(plugin.getDataFolder(), "messages.yml");
        messages = YamlConfiguration.loadConfiguration(messagesFile);
        if(!messagesFile.exists()) {
            plugin.saveResource("messages.yml", false);
        }
    }

    /**
     * Get the arenas configuration file.
     * @return Arenas configuration file.
     */
    public FileConfiguration getArenas() {
        return arenas;
    }

    /**
     * Get the main configuration file.
     * @return Main configuration file.
     */
    public FileConfiguration getConfig() {
        return config;
    }

    /**
     * Get the kits configuration file.
     * @return Kits configuration file.
     */
    public FileConfiguration getKits() {
        return kits;
    }

    /**
     * Get the levels configuration file.
     * @return Levels configuration file.
     */
    public FileConfiguration getLevels() {
        return levels;
    }

    /**
     * Get the messages configuration file.
     * @return Messages configuration file.
     */
    public FileConfiguration getMessages() {
        return messages;
    }

    /**
     * Allows us to save the config file after changes are made.
     */
    public void saveConfig() {
        try {
            config.save(configFile);
        }
        catch(IOException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * This updates the config in case changes are made.
     */
    public void reloadConfig() {
        saveConfig();
        config = YamlConfiguration.loadConfiguration(configFile);
    }
}