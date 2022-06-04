package com.github.firewolf8385.woolwars.game.teams;

import com.github.firewolf8385.woolwars.utilities.xseries.XMaterial;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.DyeColor;

/**
 * Represents a color option for a team.
 */
public enum TeamColor {
    RED(Color.RED, ChatColor.RED, "RED", XMaterial.RED_WOOL, DyeColor.RED, "R"),
    ORANGE(Color.ORANGE, ChatColor.GOLD, "ORANGE", XMaterial.ORANGE_WOOL, DyeColor.ORANGE, "O"),
    YELLOW(Color.YELLOW, ChatColor.YELLOW, "YELLOW", XMaterial.YELLOW_WOOL, DyeColor.YELLOW, "Y"),
    GREEN(Color.LIME, ChatColor.GREEN, "GREEN", XMaterial.LIME_WOOL, DyeColor.LIME, "G"),
    BLUE(Color.BLUE, ChatColor.BLUE, "BLUE", XMaterial.BLUE_WOOL, DyeColor.BLUE, "B"),
    AQUA(Color.AQUA, ChatColor.AQUA, "AQUA", XMaterial.LIGHT_BLUE_WOOL, DyeColor.LIGHT_BLUE, "A"),
    PURPLE(Color.PURPLE, ChatColor.DARK_PURPLE, "PURPLE", XMaterial.PURPLE_WOOL, DyeColor.PURPLE, "P"),
    PINK(Color.FUCHSIA, ChatColor.LIGHT_PURPLE, "PINK", XMaterial.PINK_WOOL, DyeColor.PINK, "P"),
    BLACK(Color.BLACK, ChatColor.BLACK, "BLACK", XMaterial.BLACK_WOOL, DyeColor.BLACK, "B");

    private final Color leatherColor;
    private final ChatColor chatColor;
    private final String name;
    private final XMaterial wool;
    private final DyeColor dyeColor;
    private final String abbreviation;

    TeamColor(Color leatherColor, ChatColor chatColor, String name, XMaterial wool, DyeColor dyeColor, String abbreviation) {
        this.leatherColor = leatherColor;
        this.chatColor = chatColor;
        this.name = name;
        this.wool = wool;
        this.dyeColor = dyeColor;
        this.abbreviation = abbreviation;
    }

    /**
     * Gets the abbreviation of a team.
     * @return Team's single-letter abbreviation.
     */
    public String getAbbreviation() {
        return abbreviation;
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
