package de.pongy.luckyjump.config;

import de.pongy.luckyjump.game.Game;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;

public class GameConfig extends Config {

    public static int coinsPerCheckpoint = 20;
    public static int luckyItemTime = 60;
    public static int resetY = 55;
    public static int serverReloadTime = 10;
    public static int luckyPercentage;      // the percentage a player gets an lucky item --> unlucky is 1 - that value
    public static boolean useStats;
    public static boolean gameCancelEnabled;
    public static int gameCancelTime;
    public static Location spawnA, spawnB, spectator;
    public static boolean useBungeeCord;
    public static String hubServerName;

    public GameConfig() {
        super("plugins/LuckyJump", "GameConfig.yml");
    }

    @Override
    public void setDefaults() {
        YamlConfiguration cfg = getConfiguration();
        cfg.options().copyDefaults(true);

        cfg.addDefault("lucky-item-time", 60);
        cfg.addDefault("lucky-item-percentage", 50);
        cfg.addDefault("checkpoint-material", "HEAVY_WEIGHTED_PRESSURE_PLATE");
        cfg.addDefault("win-checkpoint-material", "LIGHT_WEIGHTED_PRESSURE_PLATE");
        cfg.addDefault("coins-per-checkpoint", 20);
        cfg.addDefault("reset-Y", -5);
        cfg.addDefault("server-reload-time", 10);
        cfg.addDefault("game_cancel_enabled", true);
        cfg.addDefault("game_cancel_time", 300);
        cfg.addDefault("use_stats", true);
        cfg.addDefault("use_bungeecord", false);
        cfg.addDefault("bungee_target_servername", "Lobby");

        saveConfig(cfg);
    }

    @Override
    public void loadDefaults() {
        YamlConfiguration cfg = getConfiguration();

        Game.checkpointMaterial = Material.valueOf(cfg.getString("checkpoint-material"));
        Game.winCheckpointMaterial = Material.valueOf(cfg.getString("win-checkpoint-material"));
        coinsPerCheckpoint = cfg.getInt("coins-per-checkpoint");
        resetY = cfg.getInt("reset-Y");
        serverReloadTime = cfg.getInt("server-reload-time");
        luckyItemTime = cfg.getInt("lucky-item-time");
        spawnA = loadLocation("spawn-player1");
        spawnB = loadLocation("spawn-player2");
        spectator = loadLocation("spawn-spectator");
        luckyPercentage = cfg.getInt("lucky-item-percentage");
        gameCancelEnabled = cfg.getBoolean("game_cancel_enabled");
        gameCancelTime = cfg.getInt("game_cancel_time");
        useStats = cfg.getBoolean("use_stats");
        useBungeeCord = cfg.getBoolean("use_bungeecord");
        hubServerName = cfg.getString("bungee_target_servername");
    }
}
