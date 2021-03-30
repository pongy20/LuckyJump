package de.pongy.luckyjump;

import de.pongy.luckyjump.command.ConfigureCommand;
import de.pongy.luckyjump.command.LobbyCommand;
import de.pongy.luckyjump.command.SpawnCommand;
import de.pongy.luckyjump.command.StartCommand;
import de.pongy.luckyjump.config.GameConfig;
import de.pongy.luckyjump.config.LobbyConfig;
import de.pongy.luckyjump.config.SQLConfig;
import de.pongy.luckyjump.game.Game;
import de.pongy.luckyjump.game.GamePhase;
import de.pongy.luckyjump.game.Lobby;
import de.pongy.luckyjump.listener.GameListener;
import de.pongy.luckyjump.listener.PlayerJoin;
import de.pongy.luckyjump.listener.PlayerLeave;
import de.pongy.luckyjump.specialitems.luckyitems.*;
import de.pongy.luckyjump.specialitems.unluckyitems.*;
import de.pongy.luckyjump.stats.StatsSQL;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class LuckyJump extends JavaPlugin {

    private static LuckyJump instance;

    public static LuckyJump getInstance() {
        return instance;
    }

    // game states
    public Lobby lobby;
    public Game game;
    public GamePhase actualPhase;

    // configs
    public LobbyConfig lobbyConfig;
    public GameConfig gameConfig;
    public SQLConfig sqlConfig;

    @Override
    public void onLoad() {
        super.onLoad();
        instance = this;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        setConfigs();
        loadConfigs();

        if (LobbyConfig.lobbySpawn != null && GameConfig.spawnA != null && GameConfig.spawnB != null && GameConfig.spectator != null) {
            lobby = new Lobby(LobbyConfig.lobbySpawn);
            game = new Game();
            actualPhase = lobby;
        } else {
            System.out.println();
            Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "Game is not completely configured!");
            Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "Use /configure to initialize all values!");
            System.out.println();
        }

        registerEvents();
        registerCommands();
        registerSQL();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
    private void registerEvents() {
        PluginManager manager = Bukkit.getPluginManager();
        manager.registerEvents(new PlayerJoin(), this);
        manager.registerEvents(new PlayerLeave(), this);
        manager.registerEvents(new GameListener(), this);

        // register all lucky items
        manager.registerEvents(new KillRod(), this);
        manager.registerEvents(new FreezeEnemyItem(), this);
        new BowItem();
        new BackToCheckpoint();
        new SpeedPotionItem();
        new FreezyYourself();
        new DizzyItem();
        new JumpBoostItem();
        new SnowballItem();
        new BlindnessItem();
        new SlownessItem();
    }
    private void registerCommands() {
        this.getCommand("start").setExecutor(new StartCommand());
        this.getCommand("lobby").setExecutor(new LobbyCommand());
        this.getCommand("setspawn").setExecutor(new SpawnCommand());
        this.getCommand("configure").setExecutor(new ConfigureCommand());
    }
    private void registerSQL() {
        try {
            StatsSQL.getInstance().sql.connect();
        } catch (Exception e) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "SQL-Connection have to be configured in sql-Config file!");
            e.printStackTrace();
            return;
        }
        StatsSQL.getInstance().createTable();
    }
    private void setConfigs() {
        lobbyConfig = new LobbyConfig();
        lobbyConfig.setDefaults();

        gameConfig = new GameConfig();
        gameConfig.setDefaults();

        sqlConfig = new SQLConfig();
        sqlConfig.setDefaults();
    }
    private void loadConfigs() {
        lobbyConfig.loadDefaults();
        gameConfig.loadDefaults();
        sqlConfig.loadDefaults();
    }
}
