package com.github.firewolf8385.woolwars.game;

import com.github.firewolf8385.woolwars.WoolWars;
import com.github.firewolf8385.woolwars.game.arenas.Arena;
import com.github.firewolf8385.woolwars.game.kits.Kit;
import com.github.firewolf8385.woolwars.game.lobby.LobbyScoreboard;
import com.github.firewolf8385.woolwars.game.teams.Team;
import com.github.firewolf8385.woolwars.game.teams.TeamColor;
import com.github.firewolf8385.woolwars.game.teams.TeamManager;
import com.github.firewolf8385.woolwars.party.Party;
import com.github.firewolf8385.woolwars.utilities.LocationUtils;
import com.github.firewolf8385.woolwars.utilities.xseries.Titles;
import com.github.firewolf8385.woolwars.utilities.xseries.XSound;
import com.github.firewolf8385.woolwars.utilities.chat.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class Game {
    private final WoolWars plugin;
    private final Arena arena;
    private final Collection<Player> players = new HashSet<>();
    private final Collection<Player> spectators = new HashSet<>();
    private GameState gameState;
    private int round;
    private GameCountdown gameCountdown;
    private TeamManager teamManager;
    private final Map<Player, Integer> gameKills = new HashMap<>();
    private final Map<Player, Integer> gameBlocksPlaced = new HashMap<>();
    private final Map<Player, Integer> gameBlocksBroken = new HashMap<>();
    private final Map<Player, Integer> roundKills = new HashMap<>();
    private final Map<Player, Integer> roundBlocksBroken = new HashMap<>();
    private final Map<Player, Integer> roundBlocksPlaced = new HashMap<>();

    public Game(WoolWars plugin, Arena arena) {
        this.plugin = plugin;
        this.arena = arena;

        gameState = GameState.WAITING;
        round = 0;
        gameCountdown = new GameCountdown(plugin, this);
        teamManager = new TeamManager();
    }

    // ----------------------------------------------------------------------------------------------------------------
    public void startGame() {
        List<ArrayList<Player>> teams = new ArrayList<>();

        for(TeamColor teamColor : arena.getSpawns().keySet()) {
            teams.add(new ArrayList<>());
        }

        int count = 0;
        for(Player player : players) {
            if(count == teams.size()) {
                count = 0;
            }

            teams.get(count).add(player);
            count++;
        }

        count = 0;
        List<TeamColor> colors = new ArrayList<>(arena.getSpawns().keySet());
        for(List<Player> team : teams) {
            teamManager.createTeam(team, colors.get(count));
            count++;
        }

        startRound();
    }

    public void startRound() {
        gameState = GameState.RUNNING;
        round++;

        roundKills.clear();
        roundBlocksBroken.clear();
        roundBlocksPlaced.clear();

        new ArrayList<>(spectators).forEach(this::removeSpectator);

        for(Player player : players) {
            new GameScoreboard(plugin, player, this).update(player);
        }

        // Resets the arena.
        arena.reset();

        for(Team team : teamManager.getTeams()) {
            team.getPlayers().forEach(player -> player.teleport(arena.getSpawns().get(team.getColor())));
            team.reset();
        }

        for(Player player : getPlayers()) {
            Kit kit = plugin.getKitManager().getKit(plugin.getWoolPlayerManager().getPlayer(player).getKit());
            kit.apply(player, this);
            Titles.sendTitle(player, ChatUtils.translate("&e&lPRE ROUND"), ChatUtils.translate("&bSelect your class!"));
        }

        BukkitRunnable roundCountdown = new  BukkitRunnable() {
            int counter = 15;
            public void run() {
                if(counter == 15) {
                    sendMessage("&aRound is starting in " + counter + " seconds...");
                }

                if(gameState == GameState.END) {
                    cancel();
                }

                if(counter <= 5 && counter > 0) {
                    sendMessage("&aRound is starting in " + counter + " seconds...");
                    for (Player player : getPlayers()) {
                        player.playSound(player.getLocation(), XSound.BLOCK_NOTE_BLOCK_HAT.parseSound(), 1, 1);
                    }
                }
                if(counter == 0) {
                    for(Player player : getPlayers()) {
                        player.playSound(player.getLocation(), XSound.BLOCK_NOTE_BLOCK_HAT.parseSound(), 1, 1);
                    }

                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> runRound(), 1);
                    cancel();
                }

                counter--;
            }
        };
        roundCountdown.runTaskTimer(plugin, 0, 20);
    }

    /**
     * Runs the game countdown.
     */
    private void runRound() {
        for(Player player : getPlayers()) {
            Titles.sendTitle(player, ChatUtils.translate("&a&lROUND START"), ChatUtils.translate("&bRound " + round));
        }

        // Removes the barriers
        for(TeamColor teamColor : arena.getBarriers().keySet()) {
            for(Location location : arena.getBarriers().get(teamColor)) {
                location.getWorld().getBlockAt(location).setType(Material.AIR);
            }
        }
    }

    /**
     * Ends the round.
     * @param winner Winner of the round.
     */
    public void endRound(Team winner) {
        // Gives the winning team a point.
        winner.addPoint();

        // Checks if there is only one team left.
        if(teamManager.getTeams().size() == 1) {
            endGame(winner);
            return;
        }

        // Display round stats.
        for(Player player : getPlayers()) {
            ChatUtils.chat(player, "");
            ChatUtils.centeredChat(player, "&aRound #" + round + " Stats");
            ChatUtils.centeredChat(player, "&aKills &8- &f" + getRoundKills(player));
            ChatUtils.centeredChat(player, "&aBlocks Broken &8- &f" + getRoundBlocksBroken(player));
            ChatUtils.centeredChat(player, "&aBlocks Placed &8- &f" + getRoundBlocksPlaced(player));
            ChatUtils.chat(player, "");
        }

        // Checks if a team has enough points to win.
        if(winner.getScore() >= 3) {
            endGame(winner);
            return;
        }

        // Starts next round.
        //sendMessage("&aRound Over!");
        startRound();
    }

    /**
     * Ends the game.
     * @param winner Winner of the game.
     */
    private void endGame(Team winner) {
        gameState = GameState.END;
        sendMessage("&aGame Over");
        sendMessage("&aWinner: " + winner.getColor().getChatColor() + winner.getColor().getName());

        teamManager = new TeamManager();
        round = 0;
        gameCountdown = new GameCountdown(plugin, this);

        arena.reset();

        spectators.forEach(this::removeSpectator);

        for(Player player : getPlayers()) {
            player.teleport(LocationUtils.getSpawn(plugin));
            new LobbyScoreboard(plugin, player).update(player);

            ChatUtils.chat(player, "");
            ChatUtils.centeredChat(player, "&aGame Stats");
            ChatUtils.centeredChat(player, "&aKills &8- &f" + getGameKills(player));
            ChatUtils.centeredChat(player, "&aBlocks Broken &8- &f" + getGameBlocksBroken(player));
            ChatUtils.centeredChat(player, "&aBlocks Placed &8- &f" + getGameBlocksPlaced(player));
            ChatUtils.centeredChat(player, "");
        }

        players.clear();
        gameKills.clear();
        gameBlocksBroken.clear();
        gameBlocksPlaced.clear();
        roundKills.clear();
        roundBlocksBroken.clear();
        roundBlocksPlaced.clear();
        gameState = GameState.WAITING;
    }

    // ----------------------------------------------------------------------------------------------------------------

    public void addBlockBroken(Player player) {
        gameBlocksBroken.merge(player, 1, Integer::sum);
        roundBlocksBroken.merge(player, 1, Integer::sum);
    }

    public void addBlockPlaced(Player player) {
        gameBlocksPlaced.merge(player, 1, Integer::sum);
        roundBlocksPlaced.merge(player, 1, Integer::sum);
    }

    public void addKill(Player player) {
        gameKills.merge(player, 1, Integer::sum);
        roundKills.merge(player, 1, Integer::sum);
    }

    /**
     * Adds a player to the game.
     * If they are in a party, adds all party members.
     * @param player Player to add.
     */
    public void addPlayer(Player player) {
        // Checks if the player is in a party.
        if(plugin.getPartyManager().getParty(player) != null) {
            // If so, adds all their party members.
            Party party = plugin.getPartyManager().getParty(player);

            for(Player member : party.getMembers()) {
                players.add(member);
                member.teleport(arena.getWaitingArea());
                sendMessage("&f" + member.getName() + " &ahas joined the game! (&f"+ players.size() + "&a/&f" + arena.getMaxPlayers() + "&a)");
                new GameScoreboard(plugin, member, this).update(player);
            }
        }
        else {
            // If not, just adds themselves.
            players.add(player);
            player.teleport(arena.getWaitingArea());
            sendMessage("&f" + player.getName() + " &ahas joined the game! (&f"+ players.size() + "&a/&f" + arena.getMaxPlayers() + "&a)");
            new GameScoreboard(plugin, player, this).update(player);
        }

        // Checks if the game is at least 75% full.
        if(getPlayers().size() >= arena.getMinPlayers() && gameCountdown.getSeconds() == 30) {
            // If so, starts the countdown.
            gameCountdown.start();
            gameState = GameState.COUNTDOWN;
        }

        // Checks if the game is 100% full.
        if(getPlayers().size() == arena.getMaxPlayers() && gameCountdown.getSeconds() > 5) {
            // If so, shortens the countdown to 5 seconds.
            gameCountdown.setSeconds(5);
        }
    }

    public void addSpectator(Player player) {
        spectators.add(player);

        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        player.setAllowFlight(true);
        player.setFlying(true);
        player.setHealth(20.0);
        player.setFoodLevel(20);

        for(Player pl : getPlayers()) {
            pl.hidePlayer(player);
        }
    }

    public Arena getArena() {
        return arena;
    }

    public GameCountdown getGameCountdown() {
        return gameCountdown;
    }

    public GameState getGameState() {
        return gameState;
    }

    public int getGameKills(Player player) {
        return gameKills.getOrDefault(player, 0);
    }

    public int getGameBlocksBroken(Player player) {
        return gameBlocksBroken.getOrDefault(player, 0);
    }

    public int getGameBlocksPlaced(Player player) {
        return gameBlocksPlaced.getOrDefault(player, 0);
    }

    public String getFormattedScore(Team team) {
        String formattedScore = team.getColor().getChatColor() + "[" + team.getColor().getAbbreviation() + "] ";

        int count = 0;
        for(int i = 0; i < team.getScore(); i++) {
            formattedScore += "⬤";
            count++;
        }

        formattedScore += "&7";
        for(int i = count; i < 3; i++) {
            formattedScore += "⬤";
        }

        formattedScore += " &8(" + team.getScore() + "/3)";
        return formattedScore;
    }

    public Collection<Player> getPlayers() {
        return players;
    }

    public int getRound() {
        return round;
    }

    public int getRoundKills(Player player) {
        return roundKills.getOrDefault(player, 0);
    }

    public int getRoundBlocksBroken(Player player) {
        return roundBlocksBroken.getOrDefault(player, 0);
    }

    public int getRoundBlocksPlaced(Player player) {
        return roundBlocksPlaced.getOrDefault(player, 0);
    }

    public Collection<Player> getSpectators() {
        return spectators;
    }

    public TeamManager getTeamManager() {
        return teamManager;
    }

    public void playerDisconnect(Player player) {
        removePlayer(player);

        if(gameState == GameState.RUNNING) {
            sendMessage("&a" + player.getName() + " disconnected");

            Team team = teamManager.getTeam(player);
            team.killPlayer(player);
            team.removePlayer(player);

            if(team.getPlayers().size() == 0) {
                teamManager.removeTeam(team);
            }

            if(teamManager.getAliveTeams().size() == 1) {
                List<Team> teams = new ArrayList<>(teamManager.getAliveTeams());
                endRound(teams.get(0));
            }
        }
    }

    public void playerKilled(Player player, Player killer) {
        // Exit if they are already dead.
        if(teamManager.getTeam(player).getDeadPlayers().contains(player)) {
            return;
        }

        // Prevents stuff from breaking if the game is already over.
        if(gameState == GameState.END) {
            return;
        }

        Team team = teamManager.getTeam(player);
        team.killPlayer(player);
        sendMessage(team.getColor().getChatColor() + player.getName() + " &7was killed by " + teamManager.getTeam(killer).getColor().getChatColor() + killer.getName());

        addSpectator(player);
        addKill(killer);
    }

    public void removePlayer(Player player) {
        players.remove(player);

        if(gameState == GameState.WAITING || gameState == GameState.COUNTDOWN) {
            sendMessage("&f" + player.getName() + " &ahas left the game! (&f"+ players.size() + "&a/&f" + arena.getMaxPlayers() + "&a)");

            if(getPlayers().size() < ((arena.getMaxPlayers()/4) * 3)) {
                sendMessage("&cNot enough players! Countdown reset.");
                gameCountdown = new GameCountdown(plugin, this);
            }
        }
    }

    public void removeSpectator(Player player) {
        spectators.remove(player);

        // Stop the player from flying.
        player.setFlying(false);
        player.setAllowFlight(false);

        for(Player pl : Bukkit.getOnlinePlayers()) {
            pl.showPlayer(player);
        }
    }

    public void sendMessage(String message) {
        for(Player player : players) {
            ChatUtils.chat(player, message);
        }
    }


}