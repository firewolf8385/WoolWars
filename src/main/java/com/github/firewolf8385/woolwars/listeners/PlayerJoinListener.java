package com.github.firewolf8385.woolwars.listeners;

import com.github.firewolf8385.woolwars.WoolWars;
import com.github.firewolf8385.woolwars.game.lobby.LobbyScoreboard;
import com.github.firewolf8385.woolwars.utilities.LocationUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * This class runs a listener that is called whenever a player joins.
 * This teleports the player to spawn, reads and caches data from MySQL, and other tasks.
 */
public class PlayerJoinListener implements Listener {
    private final WoolWars plugin;

    /**
     * Creates the Listener.
     * @param plugin Instance of the plugin.
     */
    public PlayerJoinListener(WoolWars plugin) {
        this.plugin = plugin;
    }

    /**
     * Runs when the event is called.
     * @param event PlayerJoinEvent
     */
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        plugin.getWoolPlayerManager().addPlayer(player);

        // Teleport the player to the spawn if the spawn is set.
        if(plugin.getSettingsManager().getConfig().getBoolean("Spawn.Set")) {
            player.teleport(LocationUtils.getSpawn(plugin));
        }

        // Applies the Lobby Scoreboard to the player.
        new LobbyScoreboard(plugin, player);
    }
}
