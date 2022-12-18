package de.pongy.luckyjump.game;

import de.pongy.luckyjump.LuckyJump;
import de.pongy.luckyjump.bungee.BungeeConnector;
import de.pongy.luckyjump.config.GameConfig;
import de.pongy.luckyjump.config.LobbyConfig;
import de.pongy.luckyjump.language.LanguageConfig;
import de.pongy.luckyjump.language.LanguagePlaceholder;
import de.pongy.luckyjump.language.MessageKeys;
import de.pongy.luckyjump.language.PlaceholderPrefabs;
import de.pongy.luckyjump.stats.StatsService;
import de.pongy.luckyjump.utils.Timer;
import org.bukkit.*;
import org.bukkit.entity.Player;

public class Game extends GamePhase {

    public static Material checkpointMaterial = Material.HEAVY_WEIGHTED_PRESSURE_PLATE;
    public static Material winCheckpointMaterial = Material.LIGHT_WEIGHTED_PRESSURE_PLATE;

    public GameMap map;
    public LuckyJumpPlayer playerA, playerB;
    private Location checkpointA, checkpointB;
    public boolean ended;

    private Timer timer;

    public Game() {
        super(null);        // no need for spawn in this case cause of using two different players
        World world = GameConfig.spawnA.getWorld();
        map = new GameMap(world, GameConfig.spawnA, GameConfig.spawnB);
        map.setResetY(GameConfig.resetY);
        timer = new Timer();
    }

    public void startGame() {
        if (players.size() != 2) {
            System.out.println();
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Game is only configured using two players!");
            System.out.println();
            return;
        }
        // init players and checkpoints
        // TODO: maybe rework this and do it in a more variable algorithm
        playerA = players.get(0);
        playerB = players.get(1);

        checkpointA = map.getSpawnA();
        checkpointB = map.getSpawnB();

        playerA.makePlayerPlayable();
        playerB.makePlayerPlayable();

        // teleport player
        playerA.getPlayer().teleport(map.getSpawnA());
        playerB.getPlayer().teleport(map.getSpawnB());

        // update stats
        playerA.stats.addGame();
        playerB.stats.addGame();

        // send messages and play sounds
        sendGameMessage(LanguageConfig.getInstance().getMessage(MessageKeys.GAME_STARTED.getKey()));
        sendGameMessage(LanguageConfig.getInstance().getMessage(MessageKeys.GOOD_LUCK.getKey()));
        playSound(Sound.ENTITY_ENDER_DRAGON_GROWL, 10);
        checkpointA.getWorld().setTime(0);

        if (GameConfig.gameCancelEnabled) {
            LuckyJump.getInstance().gameCancel.startCancelCountdown();
        }
        timer.start();
    }
    public void sendBackToCheckpoint(Player player) {
        LuckyJumpPlayer lPlayer = getPlayer(player);
        lPlayer.stats.addFellOff();
        Location toTeleport = lPlayer.equals(playerA) ? checkpointA.clone() : checkpointB.clone();
        toTeleport.setPitch(player.getLocation().getPitch());
        toTeleport.setYaw(player.getLocation().getYaw());
        player.teleport(toTeleport);
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 10, 10);
    }
    public void updateCheckpoint(Player player, Location checkpoint) {
            LuckyJumpPlayer lPlayer = getPlayer(player);
            if (checkBlockEquals(checkpoint, checkpointA) || checkBlockEquals(checkpoint, checkpointB))
                return;
            if (lPlayer.equals(playerA))
                checkpointA = checkpoint.add(0, 0.5, 0);
            else if (lPlayer.equals(playerB))
                checkpointB = checkpoint.add(0,0.5,0);
        checkpoint.setYaw(0);
        checkpoint.setX(checkpoint.getBlockX() + 0.5);
        checkpoint.setZ(checkpoint.getBlockZ() + 0.5);
        lPlayer.addCoins(GameConfig.coinsPerCheckpoint);
        lPlayer.stats.addCheckpointHit();
        LanguagePlaceholder coinsPlaceholder = new LanguagePlaceholder(PlaceholderPrefabs.COINS.getName(), GameConfig.coinsPerCheckpoint + "");
        lPlayer.sendActionBarMessage(LanguageConfig.getInstance().getMessage(MessageKeys.COINS_RECEIVED.getKey(), coinsPlaceholder));
        lPlayer.sendMessage(LanguageConfig.getInstance().getMessage(MessageKeys.CHECKPOINT_REACHED.getKey()));
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 10);
    }
    public void winGame(Player player) {
        timer.stop();
        ended = true;
        LuckyJumpPlayer winnerPlayer = getPlayer(player);
        winnerPlayer.stats.addWin();
        // send messages and play sound
        LanguagePlaceholder playerPlaceholder = new LanguagePlaceholder(PlaceholderPrefabs.PLAYER.getName(), player.getName());
        sendGameMessage(LanguageConfig.getInstance().getMessage(MessageKeys.GAME_WON.getKey(), playerPlaceholder));

        // Currently coins are not used in lucky jump
        //playerA.sendCoinsAmount();
        //playerB.sendCoinsAmount();
        //TODO: add Coins to database

        // update stats
        StatsService.getInstance().pushStats(Bukkit.getOfflinePlayer(playerA.getPlayer().getName()), playerA.stats);
        StatsService.getInstance().pushStats(Bukkit.getOfflinePlayer(playerB.getPlayer().getName()), playerB.stats);

        playSound(Sound.ENTITY_ENDER_DRAGON_GROWL, 10);
        // teleport lobby
        Location lobbyLoc = LuckyJump.getInstance().lobby.getSpawn();
        Bukkit.getScheduler().runTask(LuckyJump.getInstance(), new Runnable() {
            @Override
            public void run() {
                playerA.getPlayer().teleport(lobbyLoc);
                playerB.getPlayer().teleport(lobbyLoc);
                playerA.clearInventory();
                playerB.clearInventory();
                playerA.clearPotionEffects();
                playerB.clearPotionEffects();
            }
        });
        // reset world and send players back to lobby --> delayed
        Bukkit.getScheduler().runTaskLater(LuckyJump.getInstance(), new Runnable() {
            @Override
            public void run() {
                for (Player all : Bukkit.getOnlinePlayers()) {
                    if (GameConfig.useBungeeCord) {
                        BungeeConnector.sendPlayerBackToHub(all);
                    } else {
                        LanguagePlaceholder placeholder = new LanguagePlaceholder(PlaceholderPrefabs.PLAYER.getName(), player.getName());
                        all.kickPlayer(LanguageConfig.getInstance().getMessage(MessageKeys.GAME_WON.getKey(), placeholder));
                    }
                }
                Bukkit.getServer().reload();
                //TODO: reset world...
            }
        }, (long) 20*GameConfig.serverReloadTime);
    }
    public void checkLuckyItem() {
        if (timer.getTime() < 2)
            return;
        if (timer.getTime() % GameConfig.luckyItemTime == 0) {
            playerA.doLuckyItem();
            playerB.doLuckyItem();
        }
    }
    public LuckyJumpPlayer getPlayer(Player player) {
        if (player.getName().equals(playerA.getPlayer().getName()))
            return playerA;
        else if (player.getName().equals(playerB.getPlayer().getName()))
            return playerB;
        return null;
    }

    @Override
    public void removePlayer(Player player) {
        super.removePlayer(player);
        LanguagePlaceholder playerPlaceholder = new LanguagePlaceholder(PlaceholderPrefabs.PLAYER.getName(), player.getName());
        sendGameMessage(LanguageConfig.getInstance().getMessage(MessageKeys.PLAYER_LEFT_RUNNING_GAME.getKey(), playerPlaceholder));
        if (players.size() <= 1 && !ended) {
            winGame(players.get(0).getPlayer());
        }
    }

    /**
     * Plays a sound for all in game players
     * TODO: add spectators
     */
    private void playSound(final Sound sound, final int volume) {
        for (LuckyJumpPlayer player : players) {
            player.getPlayer().playSound(player.getPlayer().getLocation(), sound, volume, 10);
        }
        for (Player player : Spectator.spectators) {
            player.playSound(player.getLocation(), sound, volume, 10);
        }
    }

    /**
     * This is used to check if a player already got to this checkpoint cause Locations are using floats
     * and they can be different, as if the location block is the same
     */
    private boolean checkBlockEquals(Location a, Location b) {
        return a.distance(b) <= 2;
    }
}

