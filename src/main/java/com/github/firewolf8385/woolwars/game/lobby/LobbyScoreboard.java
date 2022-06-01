package com.github.firewolf8385.woolwars.game.lobby;

import com.github.firewolf8385.woolwars.WoolWars;
import com.github.firewolf8385.woolwars.players.WoolPlayer;
import com.github.firewolf8385.woolwars.utilities.DateUtils;
import com.github.firewolf8385.woolwars.utilities.LevelUtils;
import com.github.firewolf8385.woolwars.utilities.scoreboard.CustomScoreboard;
import com.github.firewolf8385.woolwars.utilities.scoreboard.ScoreHelper;
import org.bukkit.entity.Player;

public class LobbyScoreboard extends CustomScoreboard {
    private final WoolWars plugin;

    public LobbyScoreboard(WoolWars plugin, Player player) {
        super(player);
        this.plugin = plugin;

        CustomScoreboard.getPlayers().put(player.getUniqueId(), this);
        update(player);
    }

    public void update(Player player) {
        ScoreHelper helper;

        if(ScoreHelper.hasScore(player)) {
            helper = ScoreHelper.getByPlayer(player);
        }
        else {
            helper = ScoreHelper.createScore(player);
        }

        WoolPlayer woolPlayer = plugin.getWoolPlayerManager().getPlayer(player);


        helper.setTitle("&e&lWOOL WARS");
        helper.setSlot(10, "&7" + DateUtils.currentDateToString());
        helper.setSlot(9, "");
        helper.setSlot(8, "&fLevel: " + LevelUtils.getFormattedLevel(woolPlayer.getLevel()));
        helper.setSlot(7, "");
        helper.setSlot(6, "&fProgress: &b" + LevelUtils.getFormattedExperience(woolPlayer.getExperience()) + "&7/&a" + LevelUtils.getFormattedRequiredExperience(woolPlayer.getLevel()));
        helper.setSlot(5, " " + LevelUtils.getSmallLevelBar(woolPlayer.getExperience(), woolPlayer.getLevel()));
        helper.setSlot(4, "");
        helper.setSlot(3, "&fCoins: " + "&6" + woolPlayer.getCoins());
        helper.setSlot(2, "");
        helper.setSlot(1, "&ewww.spigotmc.org");
    }
}