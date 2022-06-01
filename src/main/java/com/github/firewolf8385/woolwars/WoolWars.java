package com.github.firewolf8385.woolwars;

import com.github.firewolf8385.woolwars.listeners.PlayerJoinListener;
import com.github.firewolf8385.woolwars.listeners.PlayerQuitListener;
import com.github.firewolf8385.woolwars.players.WoolPlayerManager;
import com.github.firewolf8385.woolwars.settings.SettingsManager;
import com.github.firewolf8385.woolwars.utilities.LevelUtils;
import com.github.firewolf8385.woolwars.utilities.scoreboard.ScoreboardUpdate;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class WoolWars extends JavaPlugin {
    private final SettingsManager settingsManager;
    private final WoolPlayerManager woolPlayerManager;

    public WoolWars() {
        settingsManager = new SettingsManager(this);
        woolPlayerManager = new WoolPlayerManager(this);
    }

    @Override
    public void onEnable() {

        // Registers utilities.
        new LevelUtils(this);

        // Registers listeners.
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerQuitListener(this), this);

        // Update scoreboard
        new ScoreboardUpdate(this).runTaskTimer(this, 20L, 20L);

        // If PlaceholderAPI is installed, enables placeholders
        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new Placeholders(this).register();
        }
        else {
            Bukkit.getLogger().warning("WoolWars requires PlaceholderAPI to be installed..");
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    public SettingsManager getSettingsManager() {
        return settingsManager;
    }

    public WoolPlayerManager getWoolPlayerManager() {
        return woolPlayerManager;
    }
}
