package com.github.firewolf8385.woolwars.game.teams;

import com.github.firewolf8385.woolwars.utilities.items.XMaterial;
import org.bukkit.ChatColor;
import org.bukkit.Color;

/**
 * Represents a color option for a team.
 */
public enum TeamColor {
    RED(Color.RED, ChatColor.RED, "RED", XMaterial.RED_WOOL),
    ORANGE(Color.ORANGE, ChatColor.GOLD, "ORANGE", XMaterial.ORANGE_WOOL),
    YELLOW(Color.YELLOW, ChatColor.YELLOW, "YELLOW", XMaterial.YELLOW_WOOL),
    GREEN(Color.LIME, ChatColor.GREEN, "GREEN", XMaterial.GREEN_WOOL),
    BLUE(Color.BLUE, ChatColor.BLUE, "BLUE", XMaterial.BLUE_WOOL),
    AQUA(Color.AQUA, ChatColor.AQUA, "AQUA", XMaterial.LIGHT_BLUE_WOOL),
    PURPLE(Color.PURPLE, ChatColor.DARK_PURPLE, "PURPLE", XMaterial.PURPLE_WOOL),
    PINK(Color.FUCHSIA, ChatColor.LIGHT_PURPLE, "PINK", XMaterial.PINK_WOOL),
    BLACK(Color.BLACK, ChatColor.BLACK, "BLACK", XMaterial.BLACK_WOOL);

    private final Color leatherColor;
    private final ChatColor chatColor;
    private final String name;
    private XMaterial wool;

    TeamColor(Color leatherColor, ChatColor chatColor, String name, XMaterial wool) {
        this.leatherColor = leatherColor;
        this.chatColor = chatColor;
        this.name = name;
        this.wool = wool;
    }

    /**
     * Gets the chat color of a team.
     * @return Chat color of the team.
     */
    public ChatColor getChatColor() {
        return chatColor;
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
