package com.github.firewolf8385.woolwars.utilities;

import com.github.firewolf8385.woolwars.WoolWars;
import com.github.firewolf8385.woolwars.utilities.chat.ChatUtils;
import org.bukkit.configuration.ConfigurationSection;

public class LevelUtils {
    private static WoolWars plugin;

    public LevelUtils(WoolWars pl) {
        plugin = pl;
    }

    public static String getFormattedLevel(int level) {
        String formattedLevel = "";


        ConfigurationSection section = plugin.getSettingsManager().getLevels().getConfigurationSection("Levels");

        if(section == null) {
            return null;
        }

        for(String group : section.getKeys(false)) {
            if(Integer.parseInt(group) <= level) {
                formattedLevel = plugin.getSettingsManager().getLevels().getString("Levels." + group + ".Format")
                        .replace("%level%", level + "")
                        .replace("%star%", "✫");
            }
        }

        return ChatUtils.translate(formattedLevel);
    }

    public static String getFormattedExperience(int experience) {
        return  MathUtils.format(experience);
    }

    public static String getFormattedRequiredExperience(int level) {
        return  MathUtils.format(getRequiredExperience(level));
    }

    public static int getRequiredExperience(int level) {
        int requiredExperience = 0;


        ConfigurationSection section = plugin.getSettingsManager().getLevels().getConfigurationSection("Levels");

        if(section == null) {
            return -1;
        }

        for(String group : section.getKeys(false)) {
            if(Integer.parseInt(group) <= level) {
                requiredExperience = plugin.getSettingsManager().getLevels().getInt("Levels." + group + ".NextLevelXP");
            }
        }

        return requiredExperience;
    }

    public static String getSmallLevelBar(int currentExperience, int level) {
        int maxExperience = getRequiredExperience(level);
        int perSquare = maxExperience/10;

        String bar = "&8[&b";

        int squares = 0;
        for(int i = currentExperience; i > 0; i-= perSquare) {
            bar += "■";
            squares++;
        }

        bar += "&7";

        for(int i = squares; i < 10; i++) {
            bar += "■";
        }

        bar += "&8]";

        return ChatUtils.translate(bar);
    }
}
