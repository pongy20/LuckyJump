package de.pongy.luckyjump.utils;

import de.pongy.luckyjump.LuckyJump;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class Timer {

    private boolean running;
    private int time;
    private BukkitTask runnable;

    public Timer() {
        running = false;
        time = 0;
        runnable = null;
    }
    public void start() {
        if (running)
            return;
        running = true;
        if (runnable == null || runnable.isCancelled()) {
            runnable = new BukkitRunnable() {

                @Override
                public void run() {
                    sendTimerMessage();
                    LuckyJump.getInstance().game.checkLuckyItem();
                    if (running)
                        time++;
                }
            }.runTaskTimer(LuckyJump.getInstance(), 0, 20);
        }
    }
    public void stop() {
        if (runnable != null && !runnable.isCancelled())
            runnable.cancel();
    }
    public void pause() {
        running = false;
    }
    public void resume() {
        running = true;
    }
    public int getTime() {
        return time;
    }
    public String getTimerTime() {
        int hours = (time / 60) / 60;
        int minutes = (time / 60) % 60;
        int seconds = time % 60;

        String secondsC = seconds > 9 ? seconds + "" : "0" + seconds;
        String minutesC = minutes > 9 ? minutes + "" : "0" + minutes;
        String hoursC = hours > 9 ? hours + "" : "0" + hours;

        if (hours > 0)
            return hoursC + ":" + minutesC + ":" + secondsC;
        else if (minutes > 0)
            return minutesC + ":" + secondsC;
        else
            return seconds + "";
    }
    public boolean isRunning() {
        return running;
    }
    private void sendTimerMessage() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.RED.toString() + ChatColor.BOLD + "Zeit: " + getTimerTime()));
        }
    }
}
