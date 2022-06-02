package com.github.firewolf8385.woolwars.commands;

import com.github.firewolf8385.woolwars.WoolWars;
import com.github.firewolf8385.woolwars.game.Game;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayCMD extends AbstractCommand {
    private final WoolWars plugin;

    public PlayCMD(WoolWars plugin) {
        super("play", "", false);
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        if(args.length != 2) {
            return;
        }

        int teams = Integer.parseInt(args[0]);
        int teamSize = Integer.parseInt(args[1]);

        Game game = plugin.getGameManager().getGame(player, teams, teamSize);

        if(game == null) {
            return;
        }

        game.addPlayer(player);
    }
}