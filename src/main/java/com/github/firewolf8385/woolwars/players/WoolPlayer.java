package com.github.firewolf8385.woolwars.players;

import com.github.firewolf8385.woolwars.WoolWars;

import java.util.UUID;

public class WoolPlayer {
    private final WoolWars plugin;
    private final UUID playerUUID;

    // Data
    private int coins;
    private int level;
    private int experience;

    public WoolPlayer(WoolWars plugin, UUID playerUUID) {
        this.plugin = plugin;
        this.playerUUID = playerUUID;
    }
}
