package com.github.firewolf8385.woolwars.game.arenas;

import com.github.firewolf8385.woolwars.WoolWars;
import org.bukkit.configuration.ConfigurationSection;

import java.util.*;

/**
 * Manages all existing arenas.
 */
public class ArenaManager {
    private final WoolWars plugin;
    private final Collection<Arena> arenas = new HashSet<>();
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

        for(String id : section.getKeys(false)) {
            loadArena(id);
        }
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
    public Collection<Arena> getArenas() {
        return arenas;
    }

    /**
     * Loads an arena from arenas.yml.
     * @param id Name of the arena.
     */
    public void loadArena(String id) {
        arenas.add(new Arena(plugin, id));
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