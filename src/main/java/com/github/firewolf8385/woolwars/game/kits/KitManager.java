package com.github.firewolf8385.woolwars.game.kits;

import com.github.firewolf8385.woolwars.WoolWars;
import org.bukkit.configuration.ConfigurationSection;

import java.util.*;

public class KitManager {
    private final Collection<Kit> kits = new LinkedHashSet<>();

    public KitManager(WoolWars plugin) {
        ConfigurationSection section = plugin.getSettingsManager().getKits().getConfigurationSection("Kits");
        if(section == null) return;

        for(String kitID : section.getKeys(false)) {
            kits.add(new Kit(plugin, kitID));
        }
    }

    public Kit getKit(String id) {
        if(id.equalsIgnoreCase("random")) {
            List<Kit> randomKits = new ArrayList<>(kits);
            Collections.shuffle(randomKits);

            return randomKits.get(0);
        }
        else {
            for(Kit kit : kits) {
                if(kit.getId().equalsIgnoreCase(id)) {
                    return kit;
                }
            }
        }

        return null;
    }

    public Collection<Kit> getKits() {
        return kits;
    }
}
