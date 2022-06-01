package com.github.firewolf8385.woolwars.listeners;

import com.github.firewolf8385.woolwars.WoolWars;
import com.github.firewolf8385.woolwars.game.lobby.LobbyScoreboard;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
    private WoolWars plugin;

    public PlayerJoinListener(WoolWars plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        new LobbyScoreboard(plugin, player);
    }
}
