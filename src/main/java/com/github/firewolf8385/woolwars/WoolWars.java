package com.github.firewolf8385.woolwars;

import com.github.firewolf8385.woolwars.listeners.PlayerJoinListener;
import com.github.firewolf8385.woolwars.players.WoolPlayerManager;
import com.github.firewolf8385.woolwars.settings.SettingsManager;
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

        // Registers listeners.
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(this), this);
    }

    public SettingsManager getSettingsManager() {
        return settingsManager;
    }

    public WoolPlayerManager getWoolPlayerManager() {
        return woolPlayerManager;
    }
}
