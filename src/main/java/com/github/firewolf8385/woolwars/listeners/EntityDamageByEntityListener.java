package com.github.firewolf8385.woolwars.listeners;

import com.github.firewolf8385.woolwars.WoolWars;
import com.github.firewolf8385.woolwars.game.Game;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityDamageByEntityListener implements Listener {
    private final WoolWars plugin;

    public EntityDamageByEntityListener(WoolWars plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        // Makes sure the entity is a player.
        if(!(event.getEntity() instanceof Player)) {
            return;
        }

        // Gets the player object.
        Player player = (Player) event.getEntity();

        // Makes sure they are in a game.
        if(plugin.getGameManager().getGame(player) == null) {
            return;
        }

        // Checks if the damager is a projectile.
        if(event.getDamager() instanceof Projectile) {
            Projectile projectile = (Projectile) event.getDamager();

            // Makes sure the projectile was shot by a player.
            if(!(projectile.getShooter() instanceof Player)) {
                return;
            }

            Player shooter = (Player) projectile.getShooter();

            // Makes sure the shooter is in a game.
            if(plugin.getGameManager().getGame(shooter) == null) {
                return;
            }

            // Makes sure they are in the same game.
            if(plugin.getGameManager().getGame(player) != plugin.getGameManager().getGame(shooter)) {
                return;
            }

            // Gets the game they are in.
            Game game = plugin.getGameManager().getGame(player);

            // If they are in the same, cancels the event.
            if(game.getTeamManager().getTeam(player) == game.getTeamManager().getTeam(shooter)) {
                event.setCancelled(true);
            }
        }

        if(event.getDamager() instanceof Player) {
            Player damager = (Player) event.getDamager();

            // Makes sure the damager is in a game.
            if(plugin.getGameManager().getGame(damager) == null) {
                return;
            }

            // Makes sure they are in the same game.
            if(plugin.getGameManager().getGame(player) != plugin.getGameManager().getGame(damager)) {
                return;
            }

            // Gets the game they are in.
            Game game = plugin.getGameManager().getGame(player);

            // If they are in the same, cancels the event.
            if(game.getTeamManager().getTeam(player) == game.getTeamManager().getTeam(damager)) {
                event.setCancelled(true);
            }


            if(event.getFinalDamage() >= player.getHealth()) {
                event.setCancelled(true);

                game.playerKilled(player, damager);
            }
        }
    }
}
