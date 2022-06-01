package com.github.firewolf8385.woolwars.game.arenas;

import com.github.firewolf8385.woolwars.WoolWars;
import org.bukkit.configuration.ConfigurationSection;

import java.util.Collection;
import java.util.HashSet;

/**
 * Manages all existing arenas.
 */
public class ArenaManager {
    private final WoolWars plugin;
    private final Collection<Arena> openArenas = new HashSet<>();
    private ArenaBuilder arenaBuilder;

    /**
     * Creates the Arena Manager and loads all saved arenas.
     * @param plugin Instance of the plugin.
     */
    public ArenaManager(WoolWars plugin) {
        this.plugin = plugin;

        // Loads existing arenas from arenas.yml.
        ConfigurationSection section = plugin.getSettingsManager().getArenas().getConfigurationSection("Arenas");
        if (section == null) return;
        section.getKeys(false).forEach(this::loadArena);
    }

    /**
     * Get the current arena builder.
     * Used when an arena is being set up.
     * @return Current arena builder.
     */
    public ArenaBuilder getArenaBuilder() {
        return arenaBuilder;
    }

    /**
     * Gets all arenas currently available.
     * @return All currently open arenas.
     */
    public Collection<Arena> getOpenArenas() {
        return openArenas;
    }

    /**
     * Loads an arena from arenas.yml.
     * @param name Name of the arena.
     */
    public void loadArena(String name) {
        openArenas.add(new Arena(plugin, name));
    }

    /**
     * Sets the current arena builder.
     * Used after an arena is set up to clear it.
     * @param arenaBuilder New arena builder.
     */
    public void setArenaBuilder(ArenaBuilder arenaBuilder) {
        this.arenaBuilder = arenaBuilder;
    }
}