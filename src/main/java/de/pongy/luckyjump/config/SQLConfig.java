package de.pongy.luckyjump.config;

import org.bukkit.configuration.file.YamlConfiguration;

public class SQLConfig extends Config {

    public static String host, database, user, password, port;

    public SQLConfig() {
        super("plugins/LuckyJump", "SQL.yml");
    }

    @Override
    public void setDefaults() {
        YamlConfiguration cfg = getConfiguration();
        cfg.options().copyDefaults(true);

        cfg.addDefault("host","localhost");
        cfg.addDefault("database", "database");
        cfg.addDefault("user", "user");
        cfg.addDefault("password", "password");
        cfg.addDefault("port", "3306");

        saveConfig(cfg);
    }

    @Override
    public void loadDefaults() {
        YamlConfiguration cfg = getConfiguration();

        host = cfg.getString("host");
        database = cfg.getString("database");
        user = cfg.getString("user");
        password = cfg.getString("password");
        port = cfg.getString("port");
    }
}
