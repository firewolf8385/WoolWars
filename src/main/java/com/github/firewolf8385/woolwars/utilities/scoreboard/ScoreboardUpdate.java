package com.github.firewolf8385.woolwars.utilities.scoreboard;

import com.github.firewolf8385.woolwars.WoolWars;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * A repeating task to update all active
 * Custom Scoreboards.
 */
public class ScoreboardUpdate extends BukkitRunnable {
    @Override
    public void run() {
        for(Player player : Bukkit.getOnlinePlayers()) {
            if(ScoreHelper.hasScore(player)) {
                CustomScoreboard.getPlayers().get(player.getUniqueId()).update(player);
            }
        }
    }
}