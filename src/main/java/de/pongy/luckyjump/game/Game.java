package de.pongy.luckyjump.game;

import de.pongy.luckyjump.LuckyJump;
import de.pongy.luckyjump.config.GameConfig;
import de.pongy.luckyjump.utils.Timer;
import org.bukkit.*;
import org.bukkit.entity.Player;

public class Game extends GamePhase {

    public static Material checkpointMaterial = Material.HEAVY_WEIGHTED_PRESSURE_PLATE;
    public static Material winCheckpointMaterial = Material.LIGHT_WEIGHTED_PRESSURE_PLATE;

    public GameMap map;
    public LuckyJumpPlayer playerA, playerB;
    private Location checkpointA, checkpointB;

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

        // send messages and play sounds
        sendGameMessage(ChatColor.GREEN + "The game have been started!");
        sendGameMessage(ChatColor.GREEN.toString() + ChatColor.UNDERLINE + "Good luck and have fun!");
        playSound(Sound.ENTITY_ENDER_DRAGON_GROWL, 10);
        checkpointA.getWorld().setTime(0);

        timer.start();
    }
    public void sendBackToCheckpoint(Player player) {
        LuckyJumpPlayer lPlayer = getPlayer(player);
        player.teleport(lPlayer.equals(playerA) ? checkpointA : checkpointB);
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 10, 10);
        }
        public void updateCheckpoint(Player player, Location checkpoint) {
            LuckyJumpPlayer lPlayer = getPlayer(player);
            checkpoint.setYaw(0);
            checkpoint.setX(checkpoint.getBlockX() - 0.5);
            checkpoint.setZ(checkpoint.getBlockZ() - 0.5);
            if (checkBlockEquals(checkpoint, checkpointA) || checkBlockEquals(checkpoint, checkpointB))
                return;
            if (lPlayer.equals(playerA))
                checkpointA = checkpoint.add(0, 0.5, 0);
            else if (lPlayer.equals(playerB))
                checkpointB = checkpoint.add(0,0.5,0);
        lPlayer.addCoins(GameConfig.coinsPerCheckpoint);
        lPlayer.sendActionBarMessage(ChatColor.GREEN + "You earned " + GameConfig.coinsPerCheckpoint + " coins.");
        lPlayer.sendMessage(ChatColor.GREEN + "You reached a new Checkpoint!");
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 10);
    }
    public void winGame(Player player) {
        timer.stop();
        LuckyJumpPlayer lPlayer = getPlayer(player);
        // send messages and play sound
        sendGameMessage(ChatColor.DARK_RED + player.getName() + " won the game!");
        playerA.sendCoinsAmount();
        playerB.sendCoinsAmount();
        //TODO: add Coins to database
        playSound(Sound.ENTITY_ENDER_DRAGON_GROWL, 10);
        // teleport lobby
        Location lobbyLoc = LuckyJump.getInstance().lobby.getSpawn();
        playerA.getPlayer().teleport(lobbyLoc);
        playerB.getPlayer().teleport(lobbyLoc);

        // reset world and send players back to lobby --> delayed
        Bukkit.getScheduler().runTaskLater(LuckyJump.getInstance(), new Runnable() {
            @Override
            public void run() {
                for (Player all : Bukkit.getOnlinePlayers()) {
                    all.kickPlayer("Game ended!\n" + player.getName() + " have won the game!");
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
        sendGameMessage(ChatColor.RED + player.getName() + " left the game.");
        if (players.size() <= 1) {
            winGame(players.get(0).getPlayer());
        }
    }

    /**
     * Plays a sound for all ingame players
     * TODO: add spectators
     */
    private void playSound(Sound sound, int volume) {
        for (LuckyJumpPlayer player : players) {
            player.getPlayer().playSound(player.getPlayer().getLocation(), sound, volume, 10);
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

