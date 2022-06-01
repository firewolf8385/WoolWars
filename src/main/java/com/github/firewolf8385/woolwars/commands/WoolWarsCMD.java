package com.github.firewolf8385.woolwars.commands;


import com.github.firewolf8385.woolwars.WoolWars;
import com.github.firewolf8385.woolwars.utilities.LocationUtils;
import com.github.firewolf8385.woolwars.utilities.chat.ChatUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WoolWarsCMD extends AbstractCommand {
    private final WoolWars plugin;

    public WoolWarsCMD(WoolWars plugin) {
        super("woolwars", "ww.admin", true);
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(args.length == 0) {
            helpCMD(sender);
            return;
        }

        switch (args[0]) {
            case "info":
                infoCMD(sender);
                break;
            case "setspawn":
                setSpawn(sender);
                break;
            default:
                helpCMD(sender);
                break;
        }
    }

    private void infoCMD(CommandSender sender) {
        ChatUtils.chat(sender, "&8&m+-----------------------***-----------------------+");
        ChatUtils.centeredChat(sender, "&e&lWool Wars");
        ChatUtils.chat(sender, "");
        ChatUtils.chat(sender, "  &8» &eAuthor: &f" + plugin.getDescription().getAuthors().get(0));
        ChatUtils.chat(sender, "  &8» &eVersion: &f" + plugin.getDescription().getVersion());
        ChatUtils.chat(sender, "  &8» &eGitHub: &fhttps://github.com/firewolf8385/WoolWars");
        ChatUtils.chat(sender, "&8&m+-----------------------***-----------------------+");
    }

    private void helpCMD(CommandSender sender) {
        ChatUtils.chat(sender, "&8&m+-----------------------***-----------------------+");
        ChatUtils.centeredChat(sender, "&e&lWool Wars");
        ChatUtils.chat(sender, "  &8» &e/ww info");
        ChatUtils.chat(sender, "  &8» &e/admin");
        ChatUtils.chat(sender, "  &8» &e/arena");
        ChatUtils.chat(sender, "&8&m+-----------------------***-----------------------+");
    }

    private void setSpawn(CommandSender sender) {
        if(!(sender instanceof Player)) {
            ChatUtils.chat(sender, "&cOnly players can set the spawn location.");
            return;
        }

        Player player = (Player) sender;
        LocationUtils.setSpawn(plugin, player.getLocation());
        ChatUtils.chat(player, "&aSpawn location has been set.");
    }
}
