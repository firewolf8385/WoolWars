package com.github.firewolf8385.woolwars.utilities.items;

import com.github.firewolf8385.woolwars.utilities.chat.ChatUtils;
import com.github.firewolf8385.woolwars.utilities.xseries.XMaterial;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemBuilder {
    private ItemStack item;
    private ItemMeta meta;

    /**
     * Create a new ItemStack with Material m
     * @param m Material for the ItemStack
     */
    public ItemBuilder(Material m) {
        this(m, 1);
    }

    /**
     * Create a new ItemStack of i items with material m
     * @param m Material for the ItemStack
     * @param i Number of items in the ItemStack
     */
    public ItemBuilder(Material m, int i) {
        this(new ItemStack(m, i));
    }

    /**
     * Create a new ItemStack with XMaterial.
     * @param xMaterial XMaterial to use.
     */
    public ItemBuilder(XMaterial xMaterial) {
        this(xMaterial, 1);
    }

    /**
     * Create a new ItemStack with XMaterial and a specified amount.
     * @param xMaterial XMaterial to use.
     * @param amount Amount of the item.
     */
    public ItemBuilder(XMaterial xMaterial, int amount) {
        item = xMaterial.parseItem();
        item.setAmount(amount);
        meta = item.getItemMeta();
    }

    /**
     * Start a builder with an existing ItemStack
     * @param item ItemStack
     */
    public ItemBuilder(ItemStack item) {
        this.item = item;
        meta = item.getItemMeta();
    }

    /**
     * Add an enchantment to the item.
     * @param e Enchantment to add.
     * @param level Level of the enchantment.
     * @return ItemBuilder
     */
    public ItemBuilder addEnchantment(Enchantment e, int level) {
        addEnchantment(e, level, true);
        return this;
    }

    /**
     * Add an enchantment to the item.
     * @param e Enchantment to add.
     * @param level Level of the enchantment.
     * @return ItemBuilder
     */
    public ItemBuilder addEnchantment(Enchantment e, int level, boolean ignore) {
        meta.addEnchant(e, level, ignore);
        return this;
    }

    public ItemBuilder addFlag(ItemFlag flag) {
        meta.addItemFlags(flag);
        return this;
    }

    /**
     * Add lore to the item.
     * @param str String
     * @return ItemBuilder
     */
    public ItemBuilder addLore(String str) {
        List<String> lore = meta.getLore();

        if(lore == null) {
            lore = new ArrayList<>();
        }

        lore.add(ChatUtils.translate(str));
        meta.setLore(lore);

        return this;
    }

    /**
     * Add multiple lines of lore at once.
     * @param arr List of lore.
     * @return ItemBuilder.
     */
    public ItemBuilder addLore(List<String> arr) {
        List<String> lore = meta.getLore();

        if(lore == null) {
            lore = new ArrayList<>();
        }

        for(String str : arr) {
            lore.add(ChatUtils.translate(str));
        }
        meta.setLore(lore);

        return this;
    }

    /**
     * Get the ItemStack from the builder.
     * @return ItemStack
     */
    public ItemStack build() {
        item.setItemMeta(meta);
        return item;
    }

    /**
     * Dye a piece of leather armor.
     * @param color Color to dye the armor.
     * @return ItemBuilder.
     */
    public ItemBuilder dye(Color color) {
        if(!(meta instanceof LeatherArmorMeta)) {
            return this;
        }

        ((LeatherArmorMeta) meta).setColor(color);

        return this;
    }

    /**
     * Set the amount of items in the ItemStack
     * @param amount Amount to set.
     * @return ItemBuilder
     */
    public ItemBuilder setAmount(int amount) {
        item.setAmount(amount);
        return this;
    }

    /**
     * Set the display name of the item.
     * @param str Display name
     * @return ItemBuilder
     */
    public ItemBuilder setDisplayName(String str) {
        meta.setDisplayName(ChatUtils.translate(str));
        return this;
    }

    /**
     * Set the item stack
     * @param item item Stack
     */
    protected void setItem(ItemStack item) {
        this.item = item;
    }

    /**
     * Set the lore of an item.
     * @param lore
     * @return ItemBuilder
     */
    public ItemBuilder setLore(String... lore) {
        meta.setLore(Arrays.asList(lore));
        return this;
    }

    /**
     * Set the Material of the item.
     * @param m Material to set.
     * @return ItemBuilder
     */
    public ItemBuilder setMaterial(Material m) {
        item.setType(m);
        return this;
    }

    /**
     * Set if the item should be unbreakbale.
     * @param unbreakable Whether or not it should be unbreakable.
     * @return ItemBuilder.
     */
    public ItemBuilder setUnbreakable(boolean unbreakable) {
        meta.setUnbreakable(unbreakable);
        return this;
    }

    /**
     * Changes the material to an XMaterial
     * @param xMaterial XMaterial to set the ItemStack to.
     * @return ItemBuilder.
     */
    public ItemBuilder setXMaterial(XMaterial xMaterial) {
        item = xMaterial.parseItem();
        return this;
    }
}