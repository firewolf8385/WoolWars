package com.github.firewolf8385.woolwars.listeners;

import com.github.firewolf8385.woolwars.WoolWars;
import com.github.firewolf8385.woolwars.game.Game;
import com.github.firewolf8385.woolwars.party.Party;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * This class runs a listener that is called whenever a player leaves the server.
 * This is important for removing players from any collections they are stored in.
 */
public class PlayerQuitListener implements Listener {
    private final WoolWars plugin;

    /**
     * Creates the Listener.
     * @param plugin Instance of the plugin.
     */
    public PlayerQuitListener(WoolWars plugin) {
        this.plugin = plugin;
    }

    /**
     * Runs when the event is called.
     * @param event PlayerQuitEvent
     */
    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        plugin.getWoolPlayerManager().removePlayer(player);

        // Remove the player from their party if one exists.
        Party party = plugin.getPartyManager().getParty(player);
        if(party != null) {
            party.sendMessage("&a&lParty &8» &f" + player.getName() + " &adisconnected.");
            party.removePlayer(player);
        }

        // Removes a player from a game if they are in one.
        Game game = plugin.getGameManager().getGame(player);
        if(game != null) {
            game.playerDisconnect(player);
        }
    }
}