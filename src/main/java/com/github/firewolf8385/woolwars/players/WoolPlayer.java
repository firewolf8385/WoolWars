package com.github.firewolf8385.woolwars.players;

import com.github.firewolf8385.woolwars.WoolWars;

import java.util.UUID;

/**
 * Manages and Caches plugin data about a player.
 */
public class WoolPlayer {
    private final WoolWars plugin;
    private final UUID playerUUID;

    // Sets Default Data
    private int coins = 0;
    private int level = 1;
    private int experience = 0;
    private String kit = "random";

    /**
     * Creates the WoolPlayer object and loads data from MySQL if it exists.
     * If not, creates new entries in MySQL.
     * @param plugin Instance of the plugin.
     * @param playerUUID UUID of the player representing the WoolPlayer.
     */
    public WoolPlayer(WoolWars plugin, UUID playerUUID) {
        this.plugin = plugin;
        this.playerUUID = playerUUID;
    }

    /**
     * Gets the amount of coins the player has.
     * @return Number of coins the player has.
     */
    public int getCoins() {
        return coins;
    }

    /**
     * Gets the amount of experience the player has.
     * @return Amount of experience.
     */
    public int getExperience() {
        return experience;
    }

    /**
     * Gets the id of the kit the player has selected.
     * @return Id of the kit the player is using.
     */
    public String getKit() {
        return kit;
    }

    /**
     * Gets the current Wool Wars level of the player.
     * @return Wool Wars level.
     */
    public int getLevel() {
        return level;
    }
}
