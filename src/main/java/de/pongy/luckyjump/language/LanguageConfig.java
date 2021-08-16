package de.pongy.luckyjump.language;

import de.pongy.luckyjump.config.Config;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

public class LanguageConfig extends Config {


    public LanguageConfig() {
        super("plugins/LuckyJump, filename", "messages.yml");
    }

    @Override
    public void setDefaults() {
        YamlConfiguration cfg = getConfiguration();
        cfg.options().copyDefaults(true);

        cfg.addDefault("join_message", "&a{player} joined the game ({actual_player}/{max_player}).");

        saveConfig(cfg);
    }
    public String getMessage(String key, LanguagePlaceholder... placeholders) {

        //TODO: weiter machen

        String convertedMessage = "";

        return ChatColor.translateAlternateColorCodes('&', convertedMessage);
    }
    /**
     * @return The non-converted saved Message
     */
    private String getRawMessage(String key) {
        YamlConfiguration cfg = getConfiguration();
        return cfg.getString(key);
    }
    @Override
    public void loadDefaults() {
        // no need in this case use getMessage() instead
    }
}
