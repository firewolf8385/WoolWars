package com.github.firewolf8385.woolwars.game.arenas;

import com.github.firewolf8385.woolwars.WoolWars;
import com.github.firewolf8385.woolwars.game.teams.TeamColor;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;

import java.util.*;

/**
 * Caches data about the arena currently being set up.
 * Once completed, it saves the arena to arenas.yml and loads it into memory.
 */
public class ArenaBuilder {
    private final WoolWars plugin;
    private final String id;
    private String name;
    private final Collection<TeamColor> teams = new HashSet<>();
    private final Map<TeamColor, Location> teamSpawns = new HashMap<>();
    private final Collection<Location> blocks = new HashSet<>();
    private Location waitingArea;
    private int teamSize;

    /**
     * Creates the Arena Builder.
     * @param plugin Instance of the plugin.
     * @param id id of the Arena.
     */
    public ArenaBuilder(WoolWars plugin, String id) {
        this.plugin = plugin;
        this.id = id;
    }

    /**
     * Adds a scoring block to the arena.
     * @param location Location of the block.
     */
    public void addBlock(Location location) {
        blocks.add(location);
    }

    /**
     * Adds a team to the arena.
     * @param team Team to add.
     */
    public void addTeam(TeamColor team) {
        teams.add(team);
    }

    /**
     * Adds a team spawn to the arena.
     * @param team Team to add the spawn to.
     * @param spawn Spawn to add.
     */
    public void addSpawn(TeamColor team, Location spawn) {
        teamSpawns.put(team, spawn);
    }

    /**
     * Get the locations of scoring blocks.
     * @return All scoring block locations.
     */
    public Collection<Location> getBlocks() {
        return blocks;
    }

    /**
     * Gets the name of the arena.
     * @return Arena's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets all the team spawns.
     * @return Map of the team spawns.
     */
    public Map<TeamColor, Location> getSpawns() {
        return teamSpawns;
    }

    /**
     * Gets all teams set to the arena.
     * @return A collection of all teams.
     */
    public Collection<TeamColor> getTeams() {
        return teams;
    }

    /**
     * Get the number of players each team can have.
     * @return Size of each team.
     */
    public int getTeamSize() {
        return teamSize;
    }

    /**
     * Get the spawn location of the waiting area.
     * @return Waiting area spawn location.
     */
    public Location getWaitingArea() {
        return waitingArea;
    }

    /**
     * Sets the name of the arena.
     * @param name Name of the Arena.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the amount of players each team can have.
     * @param teamSize New size of each team.
     */
    public void setTeamSize(int teamSize) {
        this.teamSize = teamSize;
    }

    /**
     * Set the spawn location of the waiting area.
     * @param waitingArea Waiting area spawn.
     */
    public void setWaitingArea(Location waitingArea) {
        this.waitingArea = waitingArea;
    }

    /**
     * Saves the arena and loads it into memory.
     */
    public void save() {
        // Creates a spot for the new arena.
        ConfigurationSection arenaSection = plugin.getSettingsManager().getArenas().getConfigurationSection("Arenas").createSection(id);
        arenaSection.addDefault("Name", name);
        arenaSection.addDefault("TeamSize", teamSize);

        // Adds the Waiting Area location.
        ConfigurationSection waitingSection = arenaSection.createSection("Waiting");
        waitingSection.addDefault("World", waitingArea.getWorld().getName());
        waitingSection.addDefault("X", waitingArea.getX());
        waitingSection.addDefault("Y", waitingArea.getY());
        waitingSection.addDefault("Z", waitingArea.getZ());
        waitingSection.addDefault("Yaw", waitingArea.getYaw());
        waitingSection.addDefault("Pitch", waitingArea.getPitch());

        // Adds all the team colors.
        ConfigurationSection teamsSection = arenaSection.createSection("Teams");
        for(TeamColor team : teams) {
            ConfigurationSection teamSection = teamsSection.createSection(team.toString());
            teamSection.addDefault("World", teamSpawns.get(team).getWorld().getName());
            teamSection.addDefault("X", teamSpawns.get(team).getX());
            teamSection.addDefault("Y", teamSpawns.get(team).getY());
            teamSection.addDefault("Z", teamSpawns.get(team).getZ());
            teamSection.addDefault("Yaw", teamSpawns.get(team).getYaw());
            teamSection.addDefault("Pitch", teamSpawns.get(team).getPitch());
        }

        // Adds the block locations.
        ConfigurationSection blocksSection = arenaSection.createSection("blocks");
        int count = 0;
        for(Location location : blocks) {
            count++;
            ConfigurationSection blockSection = blocksSection.createSection(count + "");
            blockSection.addDefault("World", location.getWorld().getName());
            blockSection.addDefault("X", location.getX());
            blockSection.addDefault("Y", location.getY());
            blockSection.addDefault("Z", location.getZ());
            blockSection.addDefault("Yaw", location.getYaw());
            blockSection.addDefault("Pitch", location.getPitch());
        }

        // Saves and updates arenas.yml
        plugin.getSettingsManager().reloadArenas();

        // Loads arena into memory.
        plugin.getArenaManager().loadArena(id);

        // Clears the current arena builder.
        plugin.getArenaManager().setArenaBuilder(null);
    }
}