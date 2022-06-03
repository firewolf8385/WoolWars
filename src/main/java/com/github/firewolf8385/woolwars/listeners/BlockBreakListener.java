package com.github.firewolf8385.woolwars.listeners;

import com.github.firewolf8385.woolwars.WoolWars;
import com.github.firewolf8385.woolwars.game.Game;
import com.github.firewolf8385.woolwars.game.GameState;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakListener implements Listener {
    private final WoolWars plugin;

    public BlockBreakListener(WoolWars plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Game game = plugin.getGameManager().getGame(player);

        // Makes sure the player is in a game.
        if(game == null) {
            return;
        }

        // Cancels all block breaking if the game is not running.
        if(game.getGameState() != GameState.RUNNING) {
            event.setCancelled(true);
            return;
        }

        // Prevent spectators from breaking blocks.
        if(game.getSpectators().contains(player)) {
            event.setCancelled(true);
            return;
        }

        // Cancels the event if the player is not placing in a scoring area.
        if(!game.getArena().getBlocks().contains(event.getBlock().getLocation())) {
            event.setCancelled(true);
            return;
        }

        // Stop items from being dropped.
        event.setCancelled(true);
        event.getBlock().setType(Material.AIR);
    }
}
