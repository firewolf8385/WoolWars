package com.github.firewolf8385.woolwars.utilities.items;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

/**
 * A collection of utilities to make working with items easier.
 */
public class ItemUtils {

    /**
     * Creates an ItemStack from a config file.
     * @param config Config file used.
     * @param path Path to the ItemStack.
     * @return
     */
    public static ItemStack fromConfig(FileConfiguration config, String path) {
        XMaterial xMaterial = XMaterial.matchXMaterial(config.getString(path + ".Material")).get();
        int amount = config.getInt(path + ".Amount");

        // Creates the item builder.
        ItemBuilder builder = new ItemBuilder(xMaterial, amount);

        // Set the displayname
        builder.setDisplayName(config.getString(path + ".DisplayName"));

        // Set the lore.
        for(String lore : config.getStringList(path + ".Lore")) {
            builder.addLore(lore);
        }

        // Adds enchantments
        ConfigurationSection section = config.getConfigurationSection(path + "." + "Enchantments");
        if(section != null && section.getKeys(false).size() > 0) {
            for(String str : section.getKeys(false)) {
                String enchant = config.getString(path + ".Enchantments." + str + ".type");
                int level = config.getInt(path + ".Enchantments." + str + ".level");

                Enchantment enchantment = Enchantment.getByName(enchant);
                builder.addEnchantment(enchantment, level);
            }
        }

        // Set unbreakable
        builder.setUnbreakable(config.getBoolean(path + ".Unbreakable"));

        // Returns the item.
        return builder.build();
    }
}
