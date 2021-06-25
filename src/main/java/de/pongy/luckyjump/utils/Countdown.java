package de.pongy.luckyjump.utils;

import de.pongy.luckyjump.LuckyJump;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

public class Countdown {

    private boolean running;
    private int time;
    private ChatMessageType messageType;
    private LuckyJumpAction endAction;
    BukkitTask runnable;        // using a local task to avoid creating multiple while many players are connecting to the server

    public Countdown(int time, LuckyJumpAction endAction) {
        this.time = time;
        this.endAction = endAction;
        this.running = false;
        this.messageType = ChatMessageType.ACTION_BAR;
    }

    public void start() {
        if (running)
            return;
        running = true;
        if (runnable == null || runnable.isCancelled()) {
            runnable = new BukkitRunnable() {

                @Override
                public void run() {
                    sendCountdown();
                    if (time == 15 || time == 10 || time <= 5 && time > 0) {
                        LuckyJump.getInstance().lobby.sendGameMessage(ChatColor.GREEN + "Das Spiel startet in " + ChatColor.GOLD + time + ChatColor.GREEN + " Sekunden.");
                    }
                    if (time <= 3 && time > 0) {
                        playSoundForAll(Sound.BLOCK_NOTE_BLOCK_BASS);
                    }
                    if (time <= 0) {
                        endAction.doAction();
                        running = false;
                        this.cancel();
                    }
                    if (running) {
                        time--;
                    }
                }
            }.runTaskTimer(LuckyJump.getInstance(), 0, 20);
        }
    }
    public void pause() {
        running = false;
    }

    public void sendCountdown() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (running) {

                if (time <= 0) {
                    player.spigot().sendMessage(messageType, new TextComponent(ChatColor.RED.toString() + ChatColor.BOLD + "The game is about to start"));
                } else
                    player.spigot().sendMessage(messageType, new TextComponent(ChatColor.RED.toString() +  ChatColor.BOLD + "Das Spiel startet in " + getConvertedTime()));
            } else
                player.spigot().sendMessage(messageType, new TextComponent(ChatColor.RED + "Countdown wurde pausiert..."));
        }
    }
    public void playSoundForAll(Sound sound) {
        for (Player all : Bukkit.getOnlinePlayers()) {
            all.playSound(all.getLocation(), sound, 10, 10);
        }
    }
    public void setTime(int time) {
        this.time = time;
    }

    public int getTime() {
        return time;
    }

    public ChatMessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(ChatMessageType messageType) {
        this.messageType = messageType;
    }

    public String getConvertedTime() {
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
            return seconds + "";    // not very pretty to convert here adding a 0 if < 10
    }
}
