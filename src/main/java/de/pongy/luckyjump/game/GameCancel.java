package de.pongy.luckyjump.game;

import de.pongy.luckyjump.LuckyJump;
import de.pongy.luckyjump.language.LanguageConfig;
import de.pongy.luckyjump.language.LanguagePlaceholder;
import de.pongy.luckyjump.language.MessageKeys;
import de.pongy.luckyjump.language.PlaceholderPrefabs;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

/**
 * This class is used to cancel the game after some time
 * Currently a random player wins the game in this case
 */
public class GameCancel {

    public List<Integer> alerts = Arrays.asList(1,2,3,5,10,30,60,120);

    private final int cancelTime;
    private BukkitRunnable runnable;
    private boolean running;
    private int currentTime;

    public GameCancel(int cancelTime) {
        this.cancelTime = cancelTime;
        currentTime = cancelTime;
    }
    public void startCancelCountdown() {
        if (runnable == null || !running) {
            runnable = new BukkitRunnable() {
                @Override
                public void run() {
                    currentTime--;
                    if (alerts.contains(currentTime)) {
                        LuckyJump.getInstance().game.sendGameMessage(getCurrentTimeMessage());
                        LuckyJump.getInstance().game.sendGameMessage(getWarningMessage());
                    }
                    if (currentTime <= 0) {
                        cancelGame();       // cancels the time too
                    }
                }
            };
            runnable.runTaskTimer(LuckyJump.getInstance(), 0, 20);
            running = true;
        }
    }
    private String getCurrentTimeMessage() {
        LanguagePlaceholder languagePlaceholder = new LanguagePlaceholder(PlaceholderPrefabs.CURRENT_TIME.getName(), currentTime + "");
        return LanguageConfig.getInstance().getMessage(MessageKeys.GAME_CANCELED_COUNTDOWN.getKey(), languagePlaceholder);
    }
    private String getWarningMessage() {
        return LanguageConfig.getInstance().getMessage(MessageKeys.RANDOM_WINNER_WARNING.getKey());
    }
    /**
     * May have to be called when server reloads
     */
    public void stopCancelCountdown() {
        if (runnable != null) {
            runnable.cancel();
            runnable = null;
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
        LuckyJump.getInstance().game.sendGameMessage(LanguageConfig.getInstance().getMessage(MessageKeys.GAME_CANCEL_RANDOM_WINNER.getKey()));
        LuckyJump.getInstance().game.winGame(winningPlayer);
    }

}
