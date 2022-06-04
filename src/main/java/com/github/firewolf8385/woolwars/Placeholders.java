package com.github.firewolf8385.woolwars;

import com.github.firewolf8385.woolwars.game.Game;
import com.github.firewolf8385.woolwars.players.WoolPlayer;
import com.github.firewolf8385.woolwars.utilities.LevelUtils;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

/**
 * This class will be registered through the register-method in the
 * plugins onEnable-method.
 */
class Placeholders extends PlaceholderExpansion {
    private final WoolWars plugin;

    /**
     * Since we register the expansion inside our own plugin, we
     * can simply use this method here to get an instance of our
     * plugin.
     *
     * @param plugin
     *        The instance of our plugin.
     */
    public Placeholders(WoolWars plugin){
        this.plugin = plugin;
    }

    /**
     * Because this is an internal class,
     * you must override this method to let PlaceholderAPI know to not unregister your expansion class when
     * PlaceholderAPI is reloaded
     *
     * @return true to persist through reloads
     */
    @Override
    public boolean persist(){
        return true;
    }

    /**
     * Because this is a internal class, this check is not needed
     * and we can simply return {@code true}
     *
     * @return Always true since it's an internal class.
     */
    @Override
    public boolean canRegister(){
        return true;
    }

    /**
     * The name of the person who created this expansion should go here.
     * <br>For convienience do we return the author from the plugin.yml
     *
     * @return The name of the author as a String.
     */
    @Override
    public String getAuthor(){
        return plugin.getDescription().getAuthors().toString();
    }

    /**
     * The placeholder identifier should go here.
     * <br>This is what tells PlaceholderAPI to call our onRequest
     * method to obtain a value if a placeholder starts with our
     * identifier.
     * <br>This must be unique and can not contain % or _
     *
     * @return The identifier in {@code %<identifier>_<value>%} as String.
     */
    @Override
    public String getIdentifier(){
        return "ww";
    }

    /**
     * This is the version of the expansion.
     * <br>You don't have to use numbers, since it is set as a String.
     *
     * For convienience do we return the version from the plugin.yml
     *
     * @return The version as a String.
     */
    @Override
    public String getVersion(){
        return plugin.getDescription().getVersion();
    }


    @Override
    public String onPlaceholderRequest(Player player, String identifier) {
        // Level
        if(identifier.equals("level")) {
            WoolPlayer woolPlayer = plugin.getWoolPlayerManager().getPlayer(player);

            return LevelUtils.getFormattedLevel(woolPlayer.getLevel());
        }

        if(identifier.equals("team_color")) {
            Game game = plugin.getGameManager().getGame(player);

            if(game == null) {
                return "";
            }

            return game.getTeamManager().getTeam(player).getColor().getChatColor() + "";
        }

        if(identifier.equals("team_abbreviation")) {
            Game game = plugin.getGameManager().getGame(player);

            if(game == null) {
                return "";
            }

            return game.getTeamManager().getTeam(player).getColor().getChatColor() + "[" + game.getTeamManager().getTeam(player).getColor().getAbbreviation() + "] ";
        }

        return null;
    }
}