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
}
