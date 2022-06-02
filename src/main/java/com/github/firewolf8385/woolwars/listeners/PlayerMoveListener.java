package com.github.firewolf8385.woolwars.listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

public class PlayerMoveListener implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Block block = player.getLocation().clone().subtract(0, 1, 0).getBlock();

        if(block.getType() == Material.SLIME_BLOCK) {
            Vector velocity = player.getVelocity().clone();
            velocity.setY(1.5);

            player.setVelocity(velocity);
        }
    }

}