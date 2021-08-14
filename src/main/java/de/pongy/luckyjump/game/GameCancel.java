package de.pongy.luckyjump.game;

import de.pongy.luckyjump.LuckyJump;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This class is used to cancel the game after some time
 * Currently a random player wins the game in this case
 */
public class GameCancel {

    private final int cancelTime;
    private Timer timer;
    private boolean running;

    public GameCancel(int cancelTime) {
        this.cancelTime = cancelTime;
    }
    public void startCancelCountdown() {
        if (timer == null || !running) {
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    cancelGame();
                }
            }, (long) cancelTime * 1000);
            running = true;
        }
    }

    /**
     * May have to be called when server reloads
     */
    public void stopCancelCountdown() {
        if (timer != null) {
            timer.cancel();
            timer = null;
            running = false;
        }
    }

    public void cancelGame() {
        Random random = new Random();
        int winningPlayerRandom = random.nextInt(2);
        Player winningPlayer;
        if (winningPlayerRandom == 0) {
            winningPlayer = LuckyJump.getInstance().game.playerA.getPlayer();
        } else {
            winningPlayer = LuckyJump.getInstance().game.playerB.getPlayer();
        }
        stopCancelCountdown();
        LuckyJump.getInstance().game.sendGameMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE + "Das Spiel wird abgebrochen, es wird ein zufälliger Sieger ermittelt.");
        LuckyJump.getInstance().game.winGame(winningPlayer);
    }

}
