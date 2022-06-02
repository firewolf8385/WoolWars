package com.github.firewolf8385.woolwars.game.teams;

import com.github.firewolf8385.woolwars.utilities.items.XMaterial;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.DyeColor;

/**
 * Represents a color option for a team.
 */
public enum TeamColor {
    RED(Color.RED, ChatColor.RED, "RED", XMaterial.RED_WOOL, DyeColor.RED),
    ORANGE(Color.ORANGE, ChatColor.GOLD, "ORANGE", XMaterial.ORANGE_WOOL, DyeColor.ORANGE),
    YELLOW(Color.YELLOW, ChatColor.YELLOW, "YELLOW", XMaterial.YELLOW_WOOL, DyeColor.YELLOW),
    GREEN(Color.LIME, ChatColor.GREEN, "GREEN", XMaterial.GREEN_WOOL, DyeColor.GREEN),
    BLUE(Color.BLUE, ChatColor.BLUE, "BLUE", XMaterial.BLUE_WOOL, DyeColor.BLUE),
    AQUA(Color.AQUA, ChatColor.AQUA, "AQUA", XMaterial.LIGHT_BLUE_WOOL, DyeColor.LIGHT_BLUE),
    PURPLE(Color.PURPLE, ChatColor.DARK_PURPLE, "PURPLE", XMaterial.PURPLE_WOOL, DyeColor.PURPLE),
    PINK(Color.FUCHSIA, ChatColor.LIGHT_PURPLE, "PINK", XMaterial.PINK_WOOL, DyeColor.PINK),
    BLACK(Color.BLACK, ChatColor.BLACK, "BLACK", XMaterial.BLACK_WOOL, DyeColor.BLACK);

    private final Color leatherColor;
    private final ChatColor chatColor;
    private final String name;
    private XMaterial wool;
    private DyeColor dyeColor;

    TeamColor(Color leatherColor, ChatColor chatColor, String name, XMaterial wool, DyeColor dyeColor) {
        this.leatherColor = leatherColor;
        this.chatColor = chatColor;
        this.name = name;
        this.wool = wool;
        this.dyeColor = dyeColor;
    }

    /**
     * Gets the chat color of a team.
     * @return Chat color of the team.
     */
    public ChatColor getChatColor() {
        return chatColor;
    }

    /**
     * Get the dye color of the team.
     * @return Dye color.
     */
    public DyeColor getDyeColor() {
        return dyeColor;
    }

    /**
     * Gets the leather color of a team.
     * @return Leather color of the team.
     */
    public Color getLeatherColor() {
        return leatherColor;
    }

    /**
     * Gets the name of the team.
     * @return Name of the team.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the wool color of the team,
     * @return Wool color.
     */
    public XMaterial getWool() {
        return wool;
    }
}
