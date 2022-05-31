package com.github.firewolf8385.woolwars;

import com.github.firewolf8385.woolwars.players.WoolPlayerManager;
import com.github.firewolf8385.woolwars.settings.SettingsManager;
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
        // Plugin startup logic
    }

    public SettingsManager getSettingsManager() {
        return settingsManager;
    }

    public WoolPlayerManager getWoolWarsPlayerManager() {
        return woolPlayerManager;
    }
}
