package com.github.firewolf8385.woolwars.utilities;

import com.github.firewolf8385.woolwars.WoolWars;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class LocationUtils {
    /**
     * Get the spawn Location from the Config
     * @return Spawn Location
     */
    public static Location getSpawn(WoolWars plugin) {
        String world = plugin.getSettingsManager().getConfig().getString("Spawn.World");
        double x = plugin.getSettingsManager().getConfig().getDouble("Spawn.X");
        double y = plugin.getSettingsManager().getConfig().getDouble("Spawn.Y");
        double z = plugin.getSettingsManager().getConfig().getDouble("Spawn.Z");
        float pitch = (float) plugin.getSettingsManager().getConfig().getDouble("Spawn.Pitch");
        float yaw = (float) plugin.getSettingsManager().getConfig().getDouble("Spawn.Yaw");

        return new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
    }

    /**
     * Set the server spawn to the current location.
     * @param loc Location
     */
    public static void setSpawn(WoolWars plugin, Location loc) {
        String world = loc.getWorld().getName();
        double x = loc.getX();
        double y = loc.getY();
        double z = loc.getZ();
        float pitch = loc.getPitch();
        float yaw = loc.getYaw();

        plugin.getSettingsManager().getConfig().set("Spawn.X", x);
        plugin.getSettingsManager().getConfig().set("Spawn.Y", y);
        plugin.getSettingsManager().getConfig().set("Spawn.Z", z);
        plugin.getSettingsManager().getConfig().set("Spawn.Pitch", pitch);
        plugin.getSettingsManager().getConfig().set("Spawn.Yaw", yaw);
        plugin.getSettingsManager().getConfig().set("Spawn.Set", true);

        plugin.getSettingsManager().reloadConfig();
    }
}