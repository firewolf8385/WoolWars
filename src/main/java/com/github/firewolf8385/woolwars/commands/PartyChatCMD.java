package com.github.firewolf8385.woolwars.commands;

import org.apache.commons.lang.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PartyChatCMD extends AbstractCommand {

    public PartyChatCMD() {
        super("partychat", "", false);
    }


    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        player.performCommand("party chat " + StringUtils.join(args, " "));
    }
}