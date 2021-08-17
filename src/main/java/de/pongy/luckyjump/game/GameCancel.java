package de.pongy.luckyjump.game;

import de.pongy.luckyjump.LuckyJump;
import de.pongy.luckyjump.language.LanguageConfig;
import de.pongy.luckyjump.language.LanguagePlaceholder;
import de.pongy.luckyjump.language.MessageKeys;
import de.pongy.luckyjump.language.PlaceholderPrefabs;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.*;

/**
 * This class is used to cancel the game after some time
 * Currently a random player wins the game in this case
 */
public class GameCancel {

    public List<Integer> alerts = Arrays.asList(1,2,3,5,10,30,60,120);

    private final int cancelTime;
    private Timer timer;
    private boolean running;
    private int currentTime;

    public GameCancel(int cancelTime) {
        this.cancelTime = cancelTime;
        currentTime = cancelTime;
    }
    public void startCancelCountdown() {
        if (timer == null || !running) {
            timer = new Timer();
            timer.schedule(new TimerTask() {
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
            }, 0, 1000);
            running = true;
        }
    }
    private String getCurrentTimeMessage() {
        LanguagePlaceholder languagePlaceholder = new LanguagePlaceholder(PlaceholderPrefabs.CURRENT_TIME.getName(), currentTime + "");
        return LanguageConfig.getInstance().getMessage(MessageKeys.GAME_CANCELED_COUNTDOWN.getKey(), languagePlaceholder);
    }
    private String getWarningMessage() {
        return ChatColor.YELLOW.toString() + ChatColor.BOLD + "A random winner will be chosen!";
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
