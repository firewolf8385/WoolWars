package com.github.firewolf8385.woolwars.commands;

import com.github.firewolf8385.woolwars.WoolWars;
import com.github.firewolf8385.woolwars.game.arenas.Arena;
import com.github.firewolf8385.woolwars.game.arenas.ArenaBuilder;
import com.github.firewolf8385.woolwars.game.teams.TeamColor;
import com.github.firewolf8385.woolwars.utilities.chat.ChatUtils;
import com.github.firewolf8385.woolwars.utilities.items.ItemBuilder;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class ArenaCMD extends AbstractCommand {
    private final WoolWars plugin;

    public ArenaCMD(WoolWars plugin) {
        super("arena", "ww.admin", false);
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        if(args.length == 0) {
            helpCMD(player);
            return;
        }

        switch (args[0].toLowerCase()) {
            case "cancel":
                cancelCMD(player);
                break;
            case "create":
                createCMD(player, args);
                break;
            case "finish":
                finishCMD(player);
                break;
            case "setbarrier":
                setBarrier(player, args);
                break;
            case "setblocks":
                setBlocksCMD(player);
                break;
            case "setname":
                setNameCMD(player, args);
                break;
            case "setspawn":
                setSpawn(player, args);
                break;
            case "setteamsize":
                setTeamSizeCMD(player, args);
                break;
            case "setwaitingarea":
                setWaitingAreaCMD(player);
                break;
            default:
                helpCMD(player);
                break;
        }
    }

    /**
     * Runs the /arena cancel command.
     * This command cancels the arena creation process.
     * @param player Player running the command.
     */
    private void cancelCMD(Player player) {
        // Makes sure there is an arena builder present.
        if(plugin.getArenaManager().getArenaBuilder() == null) {
            ChatUtils.chat(player, "&cError &8» &cThere is no arena currently being set up.");
        }

        plugin.getArenaManager().setArenaBuilder(null);
        ChatUtils.chat(player, "&a&lWoolWars &8» &aYou have canceled the arena setup.");
    }

    /**
     * Runs the /arena create command.
     * This command starts the arena creation process.
     * @param player Player running the command.
     * @param args Command arguments.
     */
    private void createCMD(Player player, String[] args) {
        // Makes sure there is no arena builder already being set up.
        if(plugin.getArenaManager().getArenaBuilder() != null) {
            ChatUtils.chat(player, "&cError &8» &cThere is already an arena being set up.");
            return;
        }

        // Makes sure the command is being used properly.
        if(args.length == 1) {
            ChatUtils.chat(player, "&cUsage &8» &c/arena create [id]");
            return;
        }

        // Gets the arena id.
        String id = StringUtils.join(Arrays.copyOfRange(args, 1, args.length), " ");

        // Makes sure the id isn't already in use.
        for(Arena arena : plugin.getArenaManager().getArenas()) {
            if(arena.getId().equalsIgnoreCase(id)) {
                ChatUtils.chat(player, "&cError &8» &cThere is already an arena with that id.");
                return;
            }
        }

        // Starts the arena setup process.
        plugin.getArenaManager().setArenaBuilder(new ArenaBuilder(plugin, id));
        ChatUtils.chat(player, "&a&lWoolWars &8» &aCreated an arena with the id &f" + id + "&a.");
        ChatUtils.chat(player, "&a&lWoolWars &8» &aNext, set the arena name with &f/arena setname [name]&a.");
    }

    /**
     * Runs the /arena finish command.
     * This command checks if the arena is done and saves it if so.
     * @param player Player running the command.
     */
    private void finishCMD(Player player) {
        // Makes sure there an arena is being set up.
        if(plugin.getArenaManager().getArenaBuilder() == null) {
            ChatUtils.chat(player, "&cError &8» &cYou need to create an arena first! /arena create");
            return;
        }

        // Makes sure the arena has a name.
        if(plugin.getArenaManager().getArenaBuilder().getName() == null) {
            ChatUtils.chat(player, "&cError &8» &cYour arena needs a name first!");
            return;
        }

        // Makes sure the teams have a size.
        if(plugin.getArenaManager().getArenaBuilder().getTeamSize() < 1) {
            ChatUtils.chat(player, "&cError &8» &cYou need to set a team size!");
            return;
        }

        // Makes sure the waiting area was set.
        if(plugin.getArenaManager().getArenaBuilder().getWaitingArea() == null) {
            ChatUtils.chat(player, "&cError &8» &cYou need to set the waiting area spawn!");
            return;
        }

        // Makes sure at least 2 teams have spawns.
        if(plugin.getArenaManager().getArenaBuilder().getSpawns().size() < 2) {
            ChatUtils.chat(player, "&cError &8» &cYour arena requires at least 2 teams!");
            return;
        }

        // Makes sure each team has a barrier.
        if(plugin.getArenaManager().getArenaBuilder().getSpawns().size() != plugin.getArenaManager().getArenaBuilder().getBarriers().size()) {
            ChatUtils.chat(player, "&cError &8» &cNot all teams have both a spawn and a barrier!");
            return;
        }

        // Makes sure at least 1 block was set.
        if(plugin.getArenaManager().getArenaBuilder().getBlocks().size() < 1) {
            ChatUtils.chat(player, "&cError &8» &cYour arena requires at least 1 block set!");
            return;
        }

        // Saves the arena.
        ChatUtils.chat(player, "&a&lWoolWars &8» &aArena has been saved.");
        plugin.getArenaManager().getArenaBuilder().save();
    }

    private void helpCMD(Player player) {

    }

    private void setBarrier(Player player, String[] args) {
        // Makes sure there an arena is being set up.
        if(plugin.getArenaManager().getArenaBuilder() == null) {
            ChatUtils.chat(player, "&cError &8» &cYou need to create an arena first! /arena create");
            return;
        }

        // Makes sure the command is being used properly.
        if(args.length == 1) {
            ChatUtils.chat(player, "&cUsage &8» &c/arena setbarrier [team]");
            return;
        }

        // Gets the team from the command.
        String teamString = args[1].toUpperCase();

        // Makes sure the team is valid.
        boolean valid = false;
        for(TeamColor team : TeamColor.values()) {
            if(team.toString().equals(teamString.toUpperCase())) {
                valid = true;
            }
        }

        if(!valid) {
            ChatUtils.chat(player, "&cError &8» &cThat is not a valid team. Valid teams are: red, &6orange, &eyellow, &agreen, &9blue, &5purple");
            return;
        }

        // Gets the team to add the barrier too.
        TeamColor team = TeamColor.valueOf(teamString);

        ItemBuilder barrier = new ItemBuilder(Material.GLASS)
                .setDisplayName("&6Barrier")
                .addLore(team.getChatColor() + "" + team);
        player.getInventory().setItemInHand(barrier.build());

        // Checks if enough spawns have been set.
        if(plugin.getArenaManager().getArenaBuilder().getSpawns().size() < 2) {
            ChatUtils.chat(player, "&a&lWoolWars &8» &aNow travel to another team's spawn and do it again.");
            return;
        }

        ChatUtils.chat(player, "&a&lWoolWars &8» &aYou can add as many teams as you want. When you're done, setup the breakable blocks with &f/arena setblocks&a.");
    }

    private void setBlocksCMD(Player player) {
        // Makes sure there an arena is being set up.
        if(plugin.getArenaManager().getArenaBuilder() == null) {
            ChatUtils.chat(player, "&cError &8» &cYou need to create an arena first! /arena create");
            return;
        }

        ChatUtils.chat(player, "&a&lWoolWars &8» &aPlace the sponge everywhere that the players should fill in with wool. When you are done, save the arena with &f/arena finish&a.");

        ItemBuilder sponge = new ItemBuilder(Material.SPONGE)
                .setDisplayName("&6Wool Generator")
                .addLore("&7Every block you place is a")
                .addLore("&7position where players would be")
                .addLore("&7able to place wool.");
        player.getInventory().setItemInHand(sponge.build());
    }

    /**
     * Runs the /arena setname command.
     * This command sets the name of the arena.
     * @param player Player running the command.
     * @param args Command arguments.
     */
    private void setNameCMD(Player player, String[] args) {
        // Makes sure there an arena is being set up.
        if(plugin.getArenaManager().getArenaBuilder() == null) {
            ChatUtils.chat(player, "&cError &8» &cYou need to create an arena first! /arena create");
            return;
        }

        // Makes sure the command is being used properly.
        if(args.length == 1) {
            ChatUtils.chat(player, "&cUsage &8» &c/arena setname [name]");
            return;
        }

        // Gets the arena name.
        String name = StringUtils.join(Arrays.copyOfRange(args, 1, args.length), " ");

        // Sets the arena name..
        plugin.getArenaManager().getArenaBuilder().setName(name);
        ChatUtils.chat(player, "&a&lWoolWars &8» &aArena name set to &f" + name + "&a.");
        ChatUtils.chat(player, "&a&lWoolWars &8» &aNext, set the team size with &f/arena setteamsize [amount]&a.");
    }

    /**
     * Runs the /arena setspawn command.
     * This command sets the spawn point of a team in the arena.
     * @param player Player running the command.
     * @param args Command arguments.
     */
    private void setSpawn(Player player, String[] args) {
        // Makes sure there an arena is being set up.
        if(plugin.getArenaManager().getArenaBuilder() == null) {
            ChatUtils.chat(player, "&cError &8» &cYou need to create an arena first! /arena create");
            return;
        }

        // Makes sure the command is being used properly.
        if(args.length == 1) {
            ChatUtils.chat(player, "&cUsage &8» &c/arena setspawn [team]");
            return;
        }

        // Gets the team from the command.
        String teamString = args[1].toUpperCase();

        // Makes sure the team is valid.
        boolean valid = false;
        for(TeamColor team : TeamColor.values()) {
            if(team.toString().equals(teamString.toUpperCase())) {
                valid = true;
            }
        }

        if(!valid) {
            ChatUtils.chat(player, "&cError &8» &cThat is not a valid team. Valid teams are: red, &6orange, &eyellow, &agreen, &9blue, &5purple");
            return;
        }

        // Sets the team spawn.
        plugin.getArenaManager().getArenaBuilder().addSpawn(TeamColor.valueOf(teamString), player.getLocation());

        // Checks if enough spawns have been set.
        if(plugin.getArenaManager().getArenaBuilder().getSpawns().size() < 2) {
            ChatUtils.chat(player, "&a&lWoolWars &8» &aNow travel to another team's spawn and do it again.");
            return;
        }

        ChatUtils.chat(player, "&a&lWoolWars &8» &aYou can add as many teams as you want. When you're done, setup the team barriers with &f/arena setbarrier [team]&a.");
    }

    /**
     * Runs the /arena setteamsize command.
     * This command sets the max number of players each team will have.
     * @param player Player running the command.
     * @param args Command arguments.
     */
    private void setTeamSizeCMD(Player player, String[] args) {
        // Makes sure there an arena is being set up.
        if(plugin.getArenaManager().getArenaBuilder() == null) {
            ChatUtils.chat(player, "&cError &8» &cYou need to create an arena first! /arena create");
            return;
        }

        // Makes sure the command is being used properly.
        if(args.length == 1) {
            ChatUtils.chat(player, "&cUsage &8» &c/arena setteamsize [amount]");
            return;
        }

        // Gets the team size from the command.
        int teamSize = Integer.parseInt(args[1]);

        // Makes sure it's a valid number.
        if(teamSize < 1) {
            ChatUtils.chat(player, "&cError &8» &cTeams must have at least one player.");
            return;
        }

        // Sets the team size.
        plugin.getArenaManager().getArenaBuilder().setTeamSize(teamSize);
        ChatUtils.chat(player, "&a&lWoolWars &8» &aTeam size has been set to &f" + teamSize + "&a.");
        ChatUtils.chat(player, "&a&lWoolWars &8» &aNext, set the waiting area spawn with &f/arena setwaitingarea&a.");
    }

    /**
     * Runs the /arena setwaitingarea command.
     * This command sets the waiting area spawn for the new arena.
     * @param player Player running the command.
     */
    private void setWaitingAreaCMD(Player player) {
        // Makes sure there an arena is being set up.
        if(plugin.getArenaManager().getArenaBuilder() == null) {
            ChatUtils.chat(player, "&cError &8» &cYou need to create an arena first! /arena create");
            return;
        }

        // Sets the waiting area spawn.
        plugin.getArenaManager().getArenaBuilder().setWaitingArea(player.getLocation());
        ChatUtils.chat(player, "&a&lWoolWars &8» &aYou have set the waiting area spawn to your location.");
        ChatUtils.chat(player, "&a&lWoolWars &8» &aNext, set the first team's spawn with &f/arena setspawn.");
    }
}