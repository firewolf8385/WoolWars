package com.github.firewolf8385.woolwars.party;

import com.github.firewolf8385.woolwars.WoolWars;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;

public class PartyManager {
    private final WoolWars plugin;
    private final Collection<Party> parties = new ArrayList<>();

    public PartyManager(WoolWars plugin) {
        this.plugin = plugin;
    }

    public Party createParty(Player leader) {
        Party party = new Party(plugin, leader);
        parties.add(party);
        return party;
    }

    public void disbandParty(Party party) {
        parties.remove(party);
    }

    public Party getParty(Player player) {
        for(Party party : parties) {
            if(party.getMembers().contains(player)) {
                return party;
            }
        }

        return null;
    }

    public Collection<Party> getParties() {
        return parties;
    }
}
