package com.github.firewolf8385.woolwars.game.teams;

import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Represents a group of players
 * working together in a Game.
 */
public class Team {
    private final List<Player> players;
    private final Set<Player> alivePlayers;
    private final Set<Player> deadPlayers = new HashSet<>();
    private final TeamColor color;
    private int score;

    /**
     * Creates a new team with specific players.
     * @param players Players to add to the team.
     */
    public Team(List<Player> players, TeamColor color) {
        this.players = players;
        this.alivePlayers = new HashSet<>(players);
        this.color = color;

        score = 0;
    }

    /**
     * Adds a point to the team's score.
     */
    public void addPoint() {
        score++;
    }

    /**
     * Get all alive players on the team.
     * @return All alive players.
     */
    public Set<Player> getAlivePlayers() {
        return alivePlayers;
    }

    /**
     * Get the color of the team.
     * @return Color of the team.
     */
    public TeamColor getColor() {
        return color;
    }

    /**
     * Gets all dead players on the team.
     * @return All dead players.
     */
    public Set<Player> getDeadPlayers() {
        return deadPlayers;
    }

    /**
     * Gets all players on the team, alive and dead.
     * @return All players on the team.
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * Gets the team's score.
     * @return the team's score.
     */
    public int getScore() {
        return score;
    }

    /**
     * Add a player to the dead list,
     * and remove them from the alive list.
     * @param player Player to make dead.
     */
    public void killPlayer(Player player) {
        alivePlayers.remove(player);
        deadPlayers.add(player);
    }

    /**
     * Remove a player from the team.
     * @param player Player to remove.
     */
    public void removePlayer(Player player) {
        getPlayers().remove(player);
        getAlivePlayers().remove(player);
        getDeadPlayers().remove(player);
    }

    /**
     * Clears dead players and resets alive players.
     */
    public void reset() {
        alivePlayers.addAll(deadPlayers);
        deadPlayers.clear();
    }
}