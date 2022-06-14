package com.github.firewolf8385.woolwars.players;

import com.github.firewolf8385.woolwars.WoolWars;
import org.bukkit.entity.Player;

import java.util.*;

/**
 * This class manages the creation, retrieval, and destruction of
 * WoolPlayers.
 */
public class WoolPlayerManager {
    private final WoolWars plugin;
    private final Map<UUID, WoolPlayer> players = new HashMap<>();

    /**
     * Creates the WoolPlayer Manager.
     * @param plugin Instance of the plugin.
     */
    public WoolPlayerManager(WoolWars plugin) {
        this.plugin = plugin;
    }

    /**
     * Adds a player to the WoolPlayer cache.
     * @param player Player to cache.
     */
    public void addPlayer(Player player) {
        players.put(player.getUniqueId(), new WoolPlayer(plugin, player.getUniqueId()));
    }

    /**
     * Gets the WoolPlayer object of a player.
     * @param player Player to get WoolPlayer of.
     * @return Player's WoolPlayer.
     */
    public WoolPlayer getPlayer(Player player) {
        return players.get(player.getUniqueId());
    }

    /**
     * Gets a list of all cached WoolPlayers.
     * @return All saved WoolPlayer objects.
     */
    public List<WoolPlayer> getPlayers() {
        return new ArrayList<>(players.values());
    }

    /**
     * Removes a WoolPlayer.
     * @param player Player to remove WoolPlayer of.
     */
    public void removePlayer(Player player) {
        players.remove(player.getUniqueId());
    }
}
