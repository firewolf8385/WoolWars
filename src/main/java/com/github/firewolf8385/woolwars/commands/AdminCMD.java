package com.github.firewolf8385.woolwars.commands;

import com.github.firewolf8385.woolwars.WoolWars;
import com.github.firewolf8385.woolwars.game.Game;
import com.github.firewolf8385.woolwars.game.GameState;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AdminCMD extends AbstractCommand {
    private final WoolWars plugin;

    public AdminCMD(WoolWars plugin) {
        super("admin", "ww.admin", false);
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(args.length == 0) {
            return;
        }

        Player player = (Player) sender;

        switch (args[0]) {
            case "forcestart":
                Game game = plugin.getGameManager().getGame(player);

                if(game == null) {
                    return;
                }

                if(game.getGameState() != GameState.WAITING && game.getGameState() != GameState.COUNTDOWN) {
                    return;
                }

                game.startGame();
                break;
        }
    }
}
