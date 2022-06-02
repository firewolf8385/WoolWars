package com.github.firewolf8385.woolwars.commands;

import com.github.firewolf8385.woolwars.WoolWars;
import com.github.firewolf8385.woolwars.utilities.chat.ChatUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class AbstractCommand implements CommandExecutor {
    private final String commandName;
    private final String permission;
    private final boolean canConsoleUse;
    private static WoolWars plugin;

    /**
     * Creates a new AbstractCommand.
     * @param commandName Name of the command.
     * @param permission Permission required to use the command.
     * @param canConsoleUse Whether or not console can use the command.
     */
    public AbstractCommand(final String commandName, final String permission, final boolean canConsoleUse) {
        this.commandName = commandName;
        this.permission = permission;
        this.canConsoleUse = canConsoleUse;
        plugin.getCommand(commandName).setExecutor(this);
    }

    /**
     * Registers all commands in the plugin.
     * @param pl Plugin.
     */
    public static void registerCommands(WoolWars pl) {
        plugin = pl;

        new ArenaCMD(pl);
        new PartyCMD(pl);
        new PartyChatCMD();
        new PlayCMD(pl);
        new WoolWarsCMD(pl);
    }

    /**
     * Executes the command.
     * @param sender The Command Sender.
     * @param args Arguments of the command.
     */
    public abstract void execute(CommandSender sender, String[] args);

    /**
     * Processes early execution of the plugin.
     * @param sender Command Sender.
     * @param cmd The Command.
     * @param label Command Label.
     * @param args Command Arugments.
     * @return Successful Completion.
     */
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!permission.equals("") && !sender.hasPermission(permission)) {
            ChatUtils.chat(sender, "&cError &8» &cYou do not have access to that command.");
            return true;
        }
        if(!canConsoleUse && !(sender instanceof Player)) {
            ChatUtils.chat(sender, "&cError &8» &cOnly players can use that command.");
            return true;
        }
        execute(sender, args);
        return true;
    }
}