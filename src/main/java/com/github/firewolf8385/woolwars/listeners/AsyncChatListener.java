package com.github.firewolf8385.woolwars.listeners;

import com.github.firewolf8385.woolwars.WoolWars;
import com.github.firewolf8385.woolwars.game.Game;
import com.github.firewolf8385.woolwars.game.GameState;
import com.github.firewolf8385.woolwars.game.teams.Team;
import com.github.firewolf8385.woolwars.utilities.chat.ChatUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class AsyncChatListener implements Listener {
    private final WoolWars plugin;

    public AsyncChatListener(WoolWars plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {

        // Exit if the event was cancelled by another plugin.
        if(event.isCancelled()) {
            return;
        }

        Player player = event.getPlayer();
        Game game = plugin.getGameManager().getGame(player);

        // Exit if the player is not in a game.
        if(game == null) {
            return;
        }

        // Make sure game is running.
        if(game.getGameState() == GameState.WAITING || game.getGameState() == GameState.COUNTDOWN) {
            return;
        }

        String format;
        if(game.getSpectators().contains(player)) {
            format = "&7[SPECTATOR] " + player.getName() + " &8» &7" + event.getMessage();

            for(Player viewer : game.getSpectators()) {
                ChatUtils.chat(viewer, format);
            }
        }
        else {
            Team team = game.getTeamManager().getTeam(player);

            format = team.getColor().getChatColor() + "[" + team.getColor().getName() + "] &7" + player.getName() + " &8» &7" + event.getMessage();

            if(game.getArena().getTeamSize() > 1) {
                for(Player viewer : team.getPlayers()) {
                    ChatUtils.chat(viewer, format);
                }
            }
            else {
                for(Player viewer : game.getPlayers()) {
                    ChatUtils.chat(viewer, format);
                }
            }
        }

        System.out.println(format);
    }
}
