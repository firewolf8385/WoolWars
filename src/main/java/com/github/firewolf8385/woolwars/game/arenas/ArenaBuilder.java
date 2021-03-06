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
    private final Map<TeamColor, Location> teamSpawns = new HashMap<>();
    private final Map<TeamColor, Collection<Location>> barriers = new HashMap<>();
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
     * Adds a block to a team's barrier.
     * @param team Team to add barrier to.
     * @param location Location of the block to add to the barrier.
     */
    public void addBarrier(TeamColor team, Location location) {
        if(barriers.containsKey(team)) {
            barriers.get(team).add(location);
        }
        else {
            Collection<Location> barrier = new HashSet<>();
            barrier.add(location);
            barriers.put(team, barrier);
        }
    }

    /**
     * Adds a scoring block to the arena.
     * @param location Location of the block.
     */
    public void addBlock(Location location) {
        blocks.add(location);
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
     * Get a map of the barrier locations for each team.
     * @return Barrier locations of each team.
     */
    public Map<TeamColor, Collection<Location>> getBarriers() {
        return barriers;
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
        ConfigurationSection arenaSection;
        if(plugin.getSettingsManager().getArenas().getConfigurationSection("Arenas") == null) {
            arenaSection = plugin.getSettingsManager().getArenas().createSection("Arenas").createSection(id);
        }
        else {
            arenaSection = plugin.getSettingsManager().getArenas().getConfigurationSection("Arenas").createSection(id);
        }
        arenaSection.set("Name", name);
        arenaSection.set("TeamSize", teamSize);

        // Adds the Waiting Area location.
        ConfigurationSection waitingSection = arenaSection.createSection("Waiting");
        waitingSection.set("World", waitingArea.getWorld().getName());
        waitingSection.set("X", waitingArea.getX());
        waitingSection.set("Y", waitingArea.getY());
        waitingSection.set("Z", waitingArea.getZ());
        waitingSection.set("Yaw", waitingArea.getYaw());
        waitingSection.set("Pitch", waitingArea.getPitch());

        // Adds all the team colors.
        ConfigurationSection teamsSection = arenaSection.createSection("Teams");
        for(TeamColor team : teamSpawns.keySet()) {
            // Adds the team spawn.
            ConfigurationSection teamSection = teamsSection.createSection(team.toString());
            ConfigurationSection spawnSection = teamSection.createSection("Spawn");
            spawnSection.set("World", teamSpawns.get(team).getWorld().getName());
            spawnSection.set("X", teamSpawns.get(team).getX());
            spawnSection.set("Y", teamSpawns.get(team).getY());
            spawnSection.set("Z", teamSpawns.get(team).getZ());
            spawnSection.set("Yaw", teamSpawns.get(team).getYaw());
            spawnSection.set("Pitch", teamSpawns.get(team).getPitch());

            // Adds the team barriers.
            ConfigurationSection barriersSection = teamSection.createSection("Barriers");
            int count = 0;
            for(Location location : barriers.get(team)) {
                count++;
                ConfigurationSection barrierSection = barriersSection.createSection(count + "");
                barrierSection.set("World", location.getWorld().getName());
                barrierSection.set("X", location.getX());
                barrierSection.set("Y", location.getY());
                barrierSection.set("Z", location.getZ());
            }
        }

        // Adds the block locations.
        ConfigurationSection blocksSection = arenaSection.createSection("Blocks");
        int count = 0;
        for(Location location : blocks) {
            count++;
            ConfigurationSection blockSection = blocksSection.createSection(count + "");
            blockSection.set("World", location.getWorld().getName());
            blockSection.set("X", location.getX());
            blockSection.set("Y", location.getY());
            blockSection.set("Z", location.getZ());
            blockSection.set("Yaw", location.getYaw());
            blockSection.set("Pitch", location.getPitch());
        }

        // Saves and updates arenas.yml
        plugin.getSettingsManager().reloadArenas();

        // Loads arena into memory.
        plugin.getArenaManager().loadArena(id);

        // Clears the current arena builder.
        plugin.getArenaManager().setArenaBuilder(null);
    }
}