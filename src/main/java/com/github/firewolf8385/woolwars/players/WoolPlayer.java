package com.github.firewolf8385.woolwars.players;

import com.github.firewolf8385.woolwars.WoolWars;

import java.util.UUID;

public class WoolPlayer {
    private final WoolWars plugin;
    private final UUID playerUUID;

    // Data
    private int coins = 0;
    private int level = 1;
    private int experience = 0;
    private String kit = "random";

    public WoolPlayer(WoolWars plugin, UUID playerUUID) {
        this.plugin = plugin;
        this.playerUUID = playerUUID;
    }

    public int getCoins() {
        return coins;
    }

    public int getExperience() {
        return experience;
    }

    public String getKit() {
        return kit;
    }

    public int getLevel() {
        return level;
    }
}
