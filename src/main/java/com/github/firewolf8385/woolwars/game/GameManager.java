package com.github.firewolf8385.woolwars.game;

import com.github.firewolf8385.woolwars.WoolWars;
import com.github.firewolf8385.woolwars.game.arenas.Arena;
import org.bukkit.entity.Player;

import java.util.*;

public class GameManager {
    private final WoolWars plugin;
    private final Collection<Game> games = new HashSet<>();

    public GameManager(WoolWars plugin) {
        this.plugin = plugin;

        for(Arena arena : plugin.getArenaManager().getArenas()) {
            games.add(new Game(plugin, arena));
        }
    }

    /**
     * Get a random arena meeting certain criteria.
     * @param teams Number of teams the arena has.
     * @param players Number of players per team.
     * @return Random arena.
     */
    public Game getGame(Player player, int teams, int players) {
        int partyMembers = 1;

        if(plugin.getPartyManager().getParty(player) != null) {
            partyMembers = plugin.getPartyManager().getParty(player).getMembers().size();
        }

        List<Game> possibleGames = new ArrayList<>();

        for(Game game : games) {
            // Skip if the game is running.
            if(game.getGameState() != GameState.WAITING && game.getGameState() != GameState.COUNTDOWN) {
                continue;
            }

            // Skip if the game is full.
            if((game.getPlayers().size() + partyMembers) > game.getArena().getMaxPlayers()) {
                continue;
            }

            // Add to the list
            if(game.getArena().getSpawns().size() == teams && game.getArena().getTeamSize() == players) {
                possibleGames.add(game);
            }
        }

        // Shuffles list of possible games.
        Collections.shuffle(possibleGames);

        // Returns null if no games are available.
        if(possibleGames.size() == 0) {
            return null;
        }

        // Returns the top game of the shuffled list.
        return possibleGames.get(0);
    }

    public Game getGame(Player player) {
        for(Game game : games) {
            if(game.getPlayers().contains(player)) {
                return game;
            }
        }

        return null;
    }

    public Collection<Game> getGames() {
        return games;
    }
}
