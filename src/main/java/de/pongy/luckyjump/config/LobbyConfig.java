package de.pongy.luckyjump.config;

import de.pongy.luckyjump.game.Lobby;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

public class LobbyConfig extends Config{

    public static int lobbyTime = 30;
    public static int startCommandTime = 10;
    public static boolean autostart = true;
    public static Location lobbySpawn;
    public static Location winnersHologramLocation;

    public LobbyConfig() {
        super("plugins/LuckyJump", "LobbyConfig.yml");
    }

    @Override
    public void setDefaults() {
        YamlConfiguration cfg = getConfiguration();
        cfg.options().copyDefaults(true);

        cfg.addDefault("lobby-time", 30);
        cfg.addDefault("autostart", true);
        cfg.addDefault("start-command-time", 5);
        cfg.addDefault("minimal-players", 2);
        cfg.addDefault("maximal-players", 2);

        saveConfig(cfg);
    }

    @Override
    public void loadDefaults() {
        YamlConfiguration cfg = getConfiguration();
        lobbyTime = cfg.getInt("lobby-time");
        autostart = cfg.getBoolean("autostart");
        startCommandTime = cfg.getInt("start-command-time");
        lobbySpawn = loadLocation("spawn");
        winnersHologramLocation = loadLocation("winners-hologram");
        Lobby.minPlayers = cfg.getInt("minimal-players");
        Lobby.maxPlayers = cfg.getInt("maximal-players");
    }
}
