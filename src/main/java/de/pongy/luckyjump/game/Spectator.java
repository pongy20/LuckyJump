package de.pongy.luckyjump.game;

import de.pongy.luckyjump.LuckyJump;
import de.pongy.luckyjump.language.LanguageConfig;
import de.pongy.luckyjump.language.MessageKeys;
import de.pongy.luckyjump.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Spectator {

    public static List<Player> spectators = new ArrayList<>();

    public static void addPlayer(Player player) {
           spectators.add(player);
           for (Player all : Bukkit.getOnlinePlayers()) {
               player.hidePlayer(LuckyJump.getInstance(), all);
               all.hidePlayer(LuckyJump.getInstance(), player);
           }
           player.setGameMode(GameMode.SPECTATOR);
           player.setLevel(0);
           player.setExp(0);
           player.getInventory().clear();
           player.getInventory().setArmorContents(null);
           player.sendMessage(LanguageConfig.getInstance().getMessage(MessageKeys.SPECTATORMODE_JOINED.getKey()));

    }
    public static boolean isSpectator(Player player) {
        return spectators.contains(player);
    }

}
