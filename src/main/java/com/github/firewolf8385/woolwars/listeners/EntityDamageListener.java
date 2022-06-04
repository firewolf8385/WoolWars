package com.github.firewolf8385.woolwars.listeners;

import com.github.firewolf8385.woolwars.WoolWars;
import com.github.firewolf8385.woolwars.game.Game;
import com.github.firewolf8385.woolwars.game.GameState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamageListener implements Listener {
    private final WoolWars plugin;

    public EntityDamageListener(WoolWars plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        // Makes sure the entity is a player.
        if(!(event.getEntity() instanceof Player)) {
            return;
        }

        // Gets the player object.
        Player player = (Player) event.getEntity();

        // Cancels if they are in a game.
        if(plugin.getGameManager().getGame(player) == null) {
            event.setCancelled(true);
            return;
        }

        // Gets the player's game.
        Game game = plugin.getGameManager().getGame(player);

        // Cancels if the game is not in the running state.
        if(game.getGameState() != GameState.RUNNING) {
            event.setCancelled(true);
        }
    }
}
