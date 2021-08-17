package de.pongy.luckyjump.language;

import de.pongy.luckyjump.config.Config;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.HashMap;
import java.util.Map;

public class LanguageConfig extends Config {

    private static LanguageConfig instance;

    public static LanguageConfig getInstance() {
        if(instance == null) {
            instance = new LanguageConfig();
        }
        return instance;
    }

    private Map<String, String> defaultMessages;

    private LanguageConfig() {
        super("plugins/LuckyJump", "Messages.yml");
        defaultMessages = new HashMap<>();
    }

    @Override
    public void setDefaults() {
        YamlConfiguration cfg = getConfiguration();
        cfg.options().copyDefaults(true);

        for (String key : defaultMessages.keySet()) {
            if (key != null) {
                cfg.addDefault(key, defaultMessages.get(key));
            }
        }

        saveConfig(cfg);
    }
    public String getMessage(String key, LanguagePlaceholder... placeholders) {

        String rawMessage = getRawMessage(key);
        String convertedMessage = rawMessage;

        if (placeholders != null) {
            for (LanguagePlaceholder placeholder : placeholders) {
                if (convertedMessage.contains(placeholder.getPlaceholdername())) {
                    convertedMessage = convertedMessage.replace(placeholder.getPlaceholdername(),placeholder.getReplacement());
                }
            }
        }

        return ChatColor.translateAlternateColorCodes('&', convertedMessage);
    }
    /**
     * @return The non-converted saved Message
     */
    private String getRawMessage(String key) {
        YamlConfiguration cfg = getConfiguration();
        return cfg.getString(key);
    }
    public void initDefaultValues() {
        if (defaultMessages == null || !defaultMessages.isEmpty()) {    // map is null or already have been initialized
            defaultMessages = new HashMap<>();
        }
        defaultMessages.put(MessageKeys.PLAYER_JOIN.getKey(), "&a{player} joined the game ({current_players}/{max_players}).");
        defaultMessages.put(MessageKeys.PLAYER_LEFT.getKey(), "&c{player} left the game ({current_players}/{max_players}).");
        defaultMessages.put(MessageKeys.PLAYER_LEFT_RUNNING_GAME.getKey(), "&c{player} left the game!");
        defaultMessages.put(MessageKeys.MINIMUM_PLAYERS_INGAME.getKey(), "&aThere have to be at least &6{min_players} &ato start the game!");
        defaultMessages.put(MessageKeys.GAME_FORCESTARTED.getKey(), "&aYou forced the game to start!");
        defaultMessages.put(MessageKeys.STARTTIME_TOGGLED.getKey(), "&aYou changed the starttime to &6{current_time} &aseconds");
        defaultMessages.put(MessageKeys.STATS_NOT_FOUND.getKey(), "&cStats of {target} can't be found.");
        defaultMessages.put(MessageKeys.GAME_STARTED.getKey(), "&aThe game have been started!");
        defaultMessages.put(MessageKeys.GOOD_LUCK.getKey(), "&aGood luck :)");
        defaultMessages.put(MessageKeys.CHECKPOINT_REACHED.getKey(), "&aYou reached a new checkpoint!");
        defaultMessages.put(MessageKeys.COINS_RECEIVED.getKey(), "&aYou earned &6{coins}&a coins.");
        defaultMessages.put(MessageKeys.GAME_WON.getKey(), "&4{player} won the game!");
        defaultMessages.put(MessageKeys.GAME_CANCELED_COUNTDOWN.getKey(), "&cThe game will be canceled in &6{current_time}&c seconds!");
    }
    @Override
    public void loadDefaults() {
        // no need in this case use getMessage() instead
    }
}
