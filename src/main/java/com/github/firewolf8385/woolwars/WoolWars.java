package com.github.firewolf8385.woolwars;

import com.github.firewolf8385.woolwars.commands.AbstractCommand;
import com.github.firewolf8385.woolwars.game.GameManager;
import com.github.firewolf8385.woolwars.game.arenas.ArenaManager;
import com.github.firewolf8385.woolwars.game.kits.KitManager;
import com.github.firewolf8385.woolwars.listeners.*;
import com.github.firewolf8385.woolwars.party.PartyManager;
import com.github.firewolf8385.woolwars.players.WoolPlayerManager;
import com.github.firewolf8385.woolwars.settings.SettingsManager;
import com.github.firewolf8385.woolwars.utilities.LevelUtils;
import com.github.firewolf8385.woolwars.utilities.scoreboard.ScoreboardUpdate;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class WoolWars extends JavaPlugin {
    private ArenaManager arenaManager;
    private GameManager gameManager;
    private KitManager kitManager;
    private SettingsManager settingsManager;
    private WoolPlayerManager woolPlayerManager;
    private PartyManager partyManager;


    @Override
    public void onEnable() {
        settingsManager = new SettingsManager(this);
        woolPlayerManager = new WoolPlayerManager(this);
        partyManager = new PartyManager(this);
        arenaManager = new ArenaManager(this);
        kitManager = new KitManager(this);
        gameManager = new GameManager(this);

        // Registers utilities.
        new LevelUtils(this);

        // Enables bStats
        new Metrics(this, 15361);

        // Registers listeners.
        Bukkit.getPluginManager().registerEvents(new EntityDamageByEntityListener(this), this);
        Bukkit.getPluginManager().registerEvents(new BlockBreakListener(this), this);
        Bukkit.getPluginManager().registerEvents(new BlockPlaceListener(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerChatListener(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerMoveListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerQuitListener(this), this);

        // Register commands.
        AbstractCommand.registerCommands(this);

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

    public ArenaManager getArenaManager() {
        return arenaManager;
    }

    public GameManager getGameManager() {
        return gameManager;
    }

    public KitManager getKitManager() {
        return kitManager;
    }

    public SettingsManager getSettingsManager() {
        return settingsManager;
    }

    public WoolPlayerManager getWoolPlayerManager() {
        return woolPlayerManager;
    }

    public PartyManager getPartyManager() {
        return partyManager;
    }
}
