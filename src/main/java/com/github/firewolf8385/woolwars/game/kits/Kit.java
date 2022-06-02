package com.github.firewolf8385.woolwars.game.kits;

import com.github.firewolf8385.woolwars.WoolWars;
import com.github.firewolf8385.woolwars.utilities.items.ItemUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class Kit {
    private final String id;
    private final String name;
    private final ItemStack icon;
    private final Map<Integer, ItemStack> items = new HashMap<>();

    public Kit(WoolWars plugin, String id) {
        this.id = id;

        ConfigurationSection kitSection = plugin.getSettingsManager().getKits().getConfigurationSection("Kits." + id);
        name = kitSection.getString("Name");

        icon = ItemUtils.fromConfig(plugin.getSettingsManager().getKits(), "Kits." + id + ".Icon");

        ConfigurationSection itemsSection = kitSection.getConfigurationSection("Items");
        for(String slot : itemsSection.getKeys(false)) {
            items.put(Integer.parseInt(slot), ItemUtils.fromConfig(plugin.getSettingsManager().getKits(), "Kits." + id + ".Items." + slot));
        }
    }

    public ItemStack getIcon() {
        return icon;
    }

    public String getId() {
        return id;
    }

    public Map<Integer, ItemStack> getItems() {
        return items;
    }

    public String getName() {
        return name;
    }
}
