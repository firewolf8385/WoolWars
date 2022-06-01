package com.github.firewolf8385.woolwars.party;

import com.github.firewolf8385.woolwars.WoolWars;
import com.github.firewolf8385.woolwars.utilities.chat.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

/**
 * Represents a group of players outside the game.
 */
public class Party {
    private final WoolWars plugin;
    private final Map<UUID, PartyRank> members = new HashMap<>();
    private final Set<UUID> partyChatToggled = new HashSet<>();
    private Set<UUID> invites;

    /**
     * Creates the party object.
     * @param plugin Instance of the main plugin.
     * @param leader Leader of the party.
     */
    public Party(WoolWars plugin, Player leader) {
        this.plugin = plugin;
        members.put(leader.getUniqueId(), PartyRank.LEADER);
    }

    /**
     * Get all members in the party.
     * @return List of all party members.
     */
    public List<Player> getMembers() {
        List<Player> partyMembers = new ArrayList<>();

        members.keySet().forEach(uuid -> {
            if(Bukkit.getPlayer(uuid) != null) {
                partyMembers.add(Bukkit.getPlayer(uuid));
            }
        });

        return partyMembers;
    }

    /**
     * Get the rank of a player in the party.
     * Returns null if they are not in the party.
     * @param player Player to get rank of.
     * @return PartyRank of the player.
     */
    public PartyRank getRank(Player player) {
        if(members.containsKey(player.getUniqueId())) {
            return members.get(player.getUniqueId());
        }

        return null;
    }

    /**
     * Get if a player has party chat toggled.
     * @param player Player to check.
     * @return If they have party chat toggled.
     */
    public boolean hasPartyChatToggled(Player player) {
        return partyChatToggled.contains(player.getUniqueId());
    }

    /**
     * Sends a chat message to all party members
     * @param message Message to send to party members.
     */
    public void sendMessage(String message) {
        for(Player player : getMembers()) {
            ChatUtils.chat(player, message);
        }
    }

    /**
     * Toggle party chat for a player.
     * @param player Player to toggle party chat for.
     */
    public void togglePartyChat(Player player) {
        if(!partyChatToggled.contains(player.getUniqueId())) {
            partyChatToggled.add(player.getUniqueId());
        }
        else {
            partyChatToggled.remove(player.getUniqueId());
        }
    }
}