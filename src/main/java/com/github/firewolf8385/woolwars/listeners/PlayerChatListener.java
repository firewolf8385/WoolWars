package com.github.firewolf8385.woolwars.listeners;

import com.github.firewolf8385.woolwars.WoolWars;
import com.github.firewolf8385.woolwars.party.Party;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChatListener implements Listener {
    private final WoolWars plugin;

    public PlayerChatListener(WoolWars plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        Party party = plugin.getPartyManager().getParty(player);

        // Ignore if the player is not in a party.
        if(party == null) {
            return;
        }

        // Ignore if the player does not have party chat toggled on.
        if(!party.hasPartyChatToggled(player)) {
            return;
        }

        // Sends the message in party chat.
        event.setCancelled(true);
        party.sendMessage("&a&lParty &8» &f" + player.getName() + "&8: &a" + event.getMessage());
    }
}