package com.github.firewolf8385.woolwars.game.teams;

import org.bukkit.ChatColor;
import org.bukkit.Color;

/**
 * Represents a color option for a team.
 */
public enum TeamColor {
    RED(Color.RED, ChatColor.RED, "RED"),
    ORANGE(Color.ORANGE, ChatColor.GOLD, "ORANGE"),
    YELLOW(Color.YELLOW, ChatColor.YELLOW, "YELLOW"),
    GREEN(Color.LIME, ChatColor.GREEN, "GREEN"),
    BLUE(Color.BLUE, ChatColor.BLUE, "BLUE"),
    AQUA(Color.AQUA, ChatColor.AQUA, "AQUA"),
    PURPLE(Color.PURPLE, ChatColor.DARK_PURPLE, "PURPLE"),
    PINK(Color.FUCHSIA, ChatColor.LIGHT_PURPLE, "PINK"),
    BLACK(Color.BLACK, ChatColor.BLACK, "BLACK");

    private final Color leatherColor;
    private final ChatColor chatColor;
    private final String name;

    TeamColor(Color leatherColor, ChatColor chatColor, String name) {
        this.leatherColor = leatherColor;
        this.chatColor = chatColor;
        this.name = name;
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
}
