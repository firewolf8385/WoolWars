package com.github.firewolf8385.woolwars.game.kits;

import com.github.firewolf8385.woolwars.WoolWars;
import com.github.firewolf8385.woolwars.game.Game;
import com.github.firewolf8385.woolwars.game.teams.Team;
import com.github.firewolf8385.woolwars.utilities.items.ItemBuilder;
import com.github.firewolf8385.woolwars.utilities.items.ItemUtils;
import com.github.firewolf8385.woolwars.utilities.items.XMaterial;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
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

    public void apply(Player player, Game game) {
        player.getInventory().clear();
        player.getInventory().setHelmet(null);
        player.getInventory().setChestplate(null);
        player.getInventory().setLeggings(null);
        player.getInventory().setBoots(null);

        Team team = game.getTeamManager().getTeam(player);

        for(int slot : items.keySet()) {
            ItemStack item = items.get(slot);

            ItemBuilder builder = new ItemBuilder(item);
            builder.dye(team.getColor().getLeatherColor());

            if(item.getType() == XMaterial.WHITE_WOOL.parseMaterial()) {
                builder.setXMaterial(team.getColor().getWool());
                builder.setAmount(item.getAmount());
            }

            player.getInventory().setItem(slot, builder.build());
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
