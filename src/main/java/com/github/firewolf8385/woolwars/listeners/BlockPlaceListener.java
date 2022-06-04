package com.github.firewolf8385.woolwars.listeners;

import com.github.firewolf8385.woolwars.WoolWars;
import com.github.firewolf8385.woolwars.game.Game;
import com.github.firewolf8385.woolwars.game.teams.Team;
import com.github.firewolf8385.woolwars.utilities.xseries.XBlock;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
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
       arenaSetup(event);
       gamePlace(event);
    }

    private void arenaSetup(BlockPlaceEvent event) {
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

    private void gamePlace(BlockPlaceEvent event) {
        // Get the player who placed the block.
        Player player = event.getPlayer();

        // Makes sure the player is in a game.
        if(plugin.getGameManager().getGame(player) == null) {
            return;
        }

        Game game = plugin.getGameManager().getGame(player);

        // Cancels if placed in the wrong spot.
        if(!game.getArena().getBlocks().contains(event.getBlock().getLocation())) {
            event.setCancelled(true);
            return;
        }

        // Checks the area after the block is placed.
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            // Gets the player's team.
            Team team = game.getTeamManager().getTeam(player);

            // Checks if all blocks are scored.
            boolean full = true;
            for(Location location : game.getArena().getBlocks()) {
                if(XBlock.getColor(location.getBlock()) != team.getColor().getDyeColor()) {
                    full = false;
                    break;
                }
            }

            // If all blocks are the team's wool, end the round.
            if(full) {
                game.endRound(team);
            }
        }, 1);
    }
}