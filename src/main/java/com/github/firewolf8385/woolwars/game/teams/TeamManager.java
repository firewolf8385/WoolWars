package com.github.firewolf8385.woolwars.game.teams;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TeamManager {
    private final Collection<Team> teams = new ArrayList<>();
    private final Collection<Team> aliveTeams = new ArrayList<>();

    /**
     * Create a new team.
     * @param players Players to add to the team.
     * @return The new team.
     */
    public Team createTeam(List<Player> players, TeamColor color) {
        Team team = new Team(players, color);
        getTeams().add(team);
        aliveTeams.add(team);
        return team;
    }

    /**
     * Delete a team.
     * @param team Team to delete.
     */
    public void deleteTeam(Team team) {
        getTeams().remove(team);
        aliveTeams.remove(team);
    }

    /**
     * Get all teams that are still alive.
     * @return All alive teams.
     */
    public Collection<Team> getAliveTeams() {
        return aliveTeams;
    }

    /**
     * Get the team of a specific player.
     * Returns null if no team.
     * @param player Player to get team of.
     * @return Team the player is in.
     */
    public Team getTeam(Player player) {
        for(Team team : getTeams()) {
            if(team.getPlayers().contains(player)) {
                return team;
            }
        }

        return null;
    }

    /**
     * Get all existing teams in the manager.
     * @return All existing teams.
     */
    public Collection<Team> getTeams() {
        return teams;
    }

    /**
     * Kill a team.
     */
    public void killTeam(Team team) {
        aliveTeams.remove(team);
    }

    /**
     * Removes a team from the game.
     * @param team Team to remove.
     */
    public void removeTeam(Team team) {
        teams.remove(team);
        aliveTeams.remove(team);
    }
}
