package com.github.firewolf8385.woolwars.game.arenas;

import com.github.firewolf8385.woolwars.WoolWars;
import com.github.firewolf8385.woolwars.game.teams.TeamColor;
import com.github.firewolf8385.woolwars.utilities.LocationUtils;
import com.github.firewolf8385.woolwars.utilities.items.XMaterial;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.*;

/**
 * Represents an arena that a game is held in.
 */
public class Arena {
    private final String id;
    private final String name;
    private final int teamSize;
    private final Location waitingArea;
    private final Map<TeamColor, Location> spawns = new HashMap<>();
    private final Collection<Location> blocks = new HashSet<>();

    /**
     * Creates the arena object.
     * @param plugin Instance of the plugin.
     * @param id id of the arena.
     */
    public Arena(WoolWars plugin, String id) {
        this.id = id;

        FileConfiguration config = plugin.getSettingsManager().getArenas();

        name = config.getString("Arenas." + id + ".Name");
        teamSize = config.getInt("Arenas." + id + ".TeamSize");
        waitingArea = LocationUtils.fromConfig(config, "Arenas." + id + ".Waiting");

        // Loads the teams and their spawns.
        ConfigurationSection spawnsSection = config.getConfigurationSection("Arenas." + id + ".Teams");
        spawnsSection.getKeys(false).forEach(teamName -> {
            TeamColor team = TeamColor.valueOf(teamName);
            Location spawn = LocationUtils.fromConfig(config, "Arenas." + id + ".Teams." + teamName);
            spawns.put(team, spawn);
        });

        // Loads the block locations.
        ConfigurationSection blocksSection = config.getConfigurationSection("Arenas." + id + ".Blocks");
        blocksSection.getKeys(false).forEach(block -> blocks.add(LocationUtils.fromConfig(config, "Arenas." + id + ".Blocks." + block)));
    }

    /**
     * Get the locations of all scoring blocks.
     * @return Collection of scoring block locations.
     */
    public Collection<Location> getBlocks() {
        return blocks;
    }

    /**
     * Gets the id of the arena.
     * @return Id of the arena.
     */
    public String getId() {
        return id;
    }

    /**
     * Gets the maximum numbers of players in the arena.
     * @return Maximum amount of players.
     */
    public int getMaxPlayers() {
        return (teamSize * spawns.size());
    }

    /**
     * Get the name of the arena.
     * @return Get the name of the arena.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets all teams and their spawns.
     * @return Map of teams with their spawns.
     */
    public Map<TeamColor, Location> getSpawns() {
        return spawns;
    }

    /**
     * Get the max number of players on each team.
     * @return Maximum size of each team.
     */
    public int getTeamSize() {
        return teamSize;
    }

    /**
     * Get the spawn for the waiting area.
     * @return Waiting area spawn.
     */
    public Location getWaitingArea() {
        return waitingArea;
    }

    /**
     * Resets the arena.
     */
    public void reset() {
        // Set the blocks to be broken.
        for(Location location : blocks) {
            List<XMaterial> materialList = Arrays.asList(XMaterial.SNOW_BLOCK, XMaterial.QUARTZ_BLOCK, XMaterial.WHITE_WOOL);
            Collections.shuffle(materialList);

            location.getWorld().getBlockAt(location).setType(materialList.get(0).parseMaterial());
        }
    }
}