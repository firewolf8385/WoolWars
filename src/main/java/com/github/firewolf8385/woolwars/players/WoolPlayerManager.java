package com.github.firewolf8385.woolwars.players;

import com.github.firewolf8385.woolwars.WoolWars;
import org.bukkit.entity.Player;

import java.util.*;

public class WoolPlayerManager {
    private final WoolWars plugin;
    private final Map<UUID, WoolPlayer> players = new HashMap<>();

    public WoolPlayerManager(WoolWars plugin) {
        this.plugin = plugin;
    }

    public void addPlayer(Player player) {
        players.put(player.getUniqueId(), new WoolPlayer(plugin, player.getUniqueId()));
    }

    public WoolPlayer getPlayer(Player player) {
        return players.get(player.getUniqueId());
    }

    public List<WoolPlayer> getPlayers() {
        return new ArrayList<>(players.values());
    }

    public void removePlayer(Player player) {
        players.remove(player.getUniqueId());
    }
}
