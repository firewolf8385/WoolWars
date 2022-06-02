package com.github.firewolf8385.woolwars.game;

import com.github.firewolf8385.woolwars.WoolWars;
import com.github.firewolf8385.woolwars.game.arenas.Arena;
import com.github.firewolf8385.woolwars.game.teams.Team;
import com.github.firewolf8385.woolwars.game.teams.TeamColor;
import com.github.firewolf8385.woolwars.game.teams.TeamManager;
import com.github.firewolf8385.woolwars.party.Party;
import com.github.firewolf8385.woolwars.utilities.XSound;
import com.github.firewolf8385.woolwars.utilities.chat.ChatUtils;
import com.github.firewolf8385.woolwars.utilities.items.XMaterial;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class Game {
    private final WoolWars plugin;
    private final Arena arena;
    private final Collection<Player> players = new HashSet<>();
    private GameState gameState;
    private int round;
    private GameCountdown gameCountdown;
    private TeamManager teamManager;
    private Map<Team, Integer> score = new HashMap<>();

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
            Team t = teamManager.createTeam(team, colors.get(count));
            score.put(t, 0);
            count++;
        }

        startRound();
    }

    public void startRound() {
        gameState = GameState.RUNNING;
        round++;

        for(Player player : players) {
            new GameScoreboard(plugin, player, this).update(player);
        }

        // Set the blocks to be broken.
        for(Location location : arena.getBlocks()) {
            List<XMaterial> materialList = Arrays.asList(XMaterial.SNOW_BLOCK, XMaterial.QUARTZ_BLOCK, XMaterial.WHITE_WOOL);
            Collections.shuffle(materialList);

            location.getWorld().getBlockAt(location).setType(materialList.get(0).parseMaterial());
        }

        for(Team team : teamManager.getTeams()) {
            team.getPlayers().forEach(player -> player.teleport(arena.getSpawns().get(team.getColor())));
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
                else {
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

    }

    // ----------------------------------------------------------------------------------------------------------------

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
        if(getPlayers().size() >= (arena.getMaxPlayers()/4) && gameCountdown.getSeconds() == 30) {
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

    public Arena getArena() {
        return arena;
    }

    public GameCountdown getGameCountdown() {
        return gameCountdown;
    }

    public GameState getGameState() {
        return gameState;
    }

    public String getFormattedScore() {
        if(teamManager.getTeams().size() == 2) {
            List<Team> teams = new ArrayList<>(teamManager.getTeams());

            Team team1 = teams.get(0);
            Team team2 = teams.get(1);

            return ChatUtils.translate(team1.getColor().getChatColor() + String.valueOf(score.get(team1)) + " &7- " + team2.getColor().getChatColor() + String.valueOf(score.get(team2)));
        }

        return "Coming Soon";
    }

    public Collection<Player> getPlayers() {
        return players;
    }

    public int getRound() {
        return round;
    }

    public void removePlayer(Player player) {
        players.remove(player);

        if(gameState == GameState.WAITING || gameState == GameState.COUNTDOWN) {
            sendMessage("&f" + player.getName() + " &ahas left the game! (&f"+ players.size() + "&a/&f" + arena.getMaxPlayers() + "&a)");
        }
    }

    public void sendMessage(String message) {
        for(Player player : players) {
            ChatUtils.chat(player, message);
        }
    }


}