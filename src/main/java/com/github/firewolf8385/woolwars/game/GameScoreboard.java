package com.github.firewolf8385.woolwars.game;

import com.github.firewolf8385.woolwars.WoolWars;
import com.github.firewolf8385.woolwars.game.teams.Team;
import com.github.firewolf8385.woolwars.utilities.DateUtils;
import com.github.firewolf8385.woolwars.utilities.scoreboard.CustomScoreboard;
import com.github.firewolf8385.woolwars.utilities.scoreboard.ScoreHelper;
import org.bukkit.entity.Player;

public class GameScoreboard extends CustomScoreboard {
    private final WoolWars plugin;
    private final Game game;

    public GameScoreboard(WoolWars plugin, Player player, Game game) {
        super(player);
        this.plugin = plugin;
        this.game = game;

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

        switch (game.getGameState()) {
            case WAITING:
            case COUNTDOWN:
                helper.setTitle("&e&lWOOL WARS");
                helper.setSlot(8, "&7" + DateUtils.currentDateToString());
                helper.setSlot(7, "");
                helper.setSlot(6, "&fMap: &a" + game.getArena().getName());
                helper.setSlot(5, "&fPlayers: &a" + game.getPlayers().size() + "&f/&a" + game.getArena().getMaxPlayers());
                helper.setSlot(4, "");

                if(game.getGameState() == GameState.COUNTDOWN) {
                    helper.setSlot(3, "&fStarting in &a" + game.getGameCountdown().getSeconds() +  "s");
                }
                else {
                    helper.setSlot(3, "&fWaiting for players");
                }

                helper.setSlot(2, "");
                helper.setSlot(1, "&ewww.spigotmc.org");
                break;
            case RUNNING:
                helper.setTitle("&e&lWOOL WARS");
                helper.setSlot(15, "&7" + DateUtils.currentDateToString());
                helper.setSlot(14, "");
                helper.setSlot(13, "&fRound: &a" + game.getRound());
                helper.setSlot(12, "&fMap: &a" + game.getArena().getName());
                helper.setSlot(11, "");

                int slot = 10;
                for(Team team : game.getTeamManager().getTeams()) {
                    helper.setSlot(slot, game.getFormattedScore(team));
                    slot--;
                }

                helper.setSlot(2, "");
                helper.setSlot(1, "&ewww.spigotmc.org");
                break;
            default:
                helper.setTitle("&e&lWOOL WARS");
                helper.setSlot(2, "");
                helper.setSlot(1, "&ewww.spigotmc.org");
                break;
        }
    }
}