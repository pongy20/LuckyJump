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
        super("plugins/LuckyJump, filename", "messages.yml");
        defaultMessages = new HashMap<>();
    }

    @Override
    public void setDefaults() {
        YamlConfiguration cfg = getConfiguration();
        cfg.options().copyDefaults(true);

        for (Map.Entry<String, String> pair : defaultMessages.entrySet()) {
            cfg.addDefault(pair.getKey(), pair.getValue());
        }

        saveConfig(cfg);
    }
    public String getMessage(String key, LanguagePlaceholder... placeholders) {

        String rawMessage = getRawMessage(key);
        String convertedMessage = rawMessage;

        if (placeholders != null) {
            for (LanguagePlaceholder placeholder : placeholders) {
                if (convertedMessage.contains(placeholder.getPlaceholdername())) {
                    convertedMessage.replace(placeholder.getPlaceholdername(),placeholder.getReplacement());
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
            return;
        }
        defaultMessages.put(MessageKeys.PLAYER_JOIN.getKey(), "&a{player} joined the game ({current_players}/{max_players}).");
        defaultMessages.put(MessageKeys.PLAYER_LEFT.getKey(), "&c{player} left the game ({current_players}/{max_players}).");
        defaultMessages.put(MessageKeys.MINIMUM_PLAYERS_INGAME.getKey(), "&aThere have to be at least &6{min_players} &ato start the game!");
        defaultMessages.put(MessageKeys.GAME_FORCESTARTED.getKey(), "&aYou forced the game to start!");
        defaultMessages.put(MessageKeys.STARTTIME_TOGGLED.getKey(), "&aYou changed the starttime to &6{current_seconds} &aseconds");
        defaultMessages.put(MessageKeys.STATS_NOT_FOUND.getKey(), "&cStats of {target} can't be found.");
    }
    @Override
    public void loadDefaults() {
        // no need in this case use getMessage() instead
    }
}
