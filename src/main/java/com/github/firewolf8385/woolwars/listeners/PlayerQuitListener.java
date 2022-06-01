package com.github.firewolf8385.woolwars.listeners;

import com.github.firewolf8385.woolwars.WoolWars;
import com.github.firewolf8385.woolwars.party.Party;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {
    private final WoolWars plugin;

    public PlayerQuitListener(WoolWars plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        plugin.getWoolPlayerManager().removePlayer(player);

        // Remove the player from their party if one exists.
        Party party = plugin.getPartyManager().getParty(player);
        if(party != null) {
            party.sendMessage("&aParty &8» &f" + player.getName() + " &adisconnected.");
            party.removePlayer(player);
        }
    }
}
