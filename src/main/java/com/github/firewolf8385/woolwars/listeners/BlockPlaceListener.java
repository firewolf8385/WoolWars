package com.github.firewolf8385.woolwars.listeners;

import com.github.firewolf8385.woolwars.WoolWars;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

public class BlockPlaceListener implements Listener {
    private final WoolWars plugin;

    public BlockPlaceListener(WoolWars plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {

        // Makes sure there is an arena being set up.
        if(plugin.getArenaManager().getArenaBuilder() == null) {
            return;
        }

        // Get the player who placed the block.
        Player player = event.getPlayer();

        // Get the item they are holding.
        ItemStack item = player.getInventory().getItemInHand();

        // Exit if the item is null.
        if(item == null) {
            return;
        }

        // Exit if item meta is null.
        if(item.getItemMeta() == null) {
            return;
        }

        // Gets the name of the item.
        String itemName = ChatColor.stripColor(item.getItemMeta().getDisplayName());

        // Exit if the item does not have a custom name.
        if(itemName == null) {
            return;
        }

        // Makes sure the placed block is sponge and that the sponge item is a wool generator.
        if(event.getBlock().getType() != Material.SPONGE || !itemName.equals("Wool Generator")) {
            return;
        }

        // Adds the location to the block list.
        plugin.getArenaManager().getArenaBuilder().addBlock(event.getBlock().getLocation());
    }
}