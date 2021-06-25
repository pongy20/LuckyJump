package de.pongy.luckyjump.specialitems;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class LuckyItem {

    public static List<LuckyItem> items = new ArrayList<>();

    public String name;
    public boolean lucky;

    public LuckyItem(String name, boolean lucky) {
        this.name = name;
        this.lucky = lucky;
        items.add(this);
    }

    public void giveItem(Player player) {
        if (lucky) {
            player.sendMessage(ChatColor.GREEN + "Du hattest Glück!!!");
            player.sendMessage(ChatColor.GREEN.toString() + ChatColor.BOLD + "Du hast " + ChatColor.GOLD.toString() + ChatColor.UNDERLINE + name + ChatColor.RESET + ChatColor.GREEN.toString() + ChatColor.BOLD + " erhalten!");
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 10, 10);
        } else {
            player.sendMessage(ChatColor.RED + "Du hattest kein Glück!!!");
            player.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD + "Du hast " + ChatColor.GOLD.toString() + ChatColor.UNDERLINE + name + ChatColor.RESET + ChatColor.RED.toString() + ChatColor.BOLD + " erhalten!");
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 10, 10);
        }
    }
    public static List<LuckyItem> getLuckyItems() {
        List<LuckyItem> toReturn = new ArrayList<>();
        for (LuckyItem item : items) {
            if (item.lucky)
                toReturn.add(item);
        }
        return toReturn;
    }
    public static List<LuckyItem> getUnluckyItems() {
        List<LuckyItem> toReturn = new ArrayList<>();
        for (LuckyItem item : items) {
            if (!item.lucky)
                toReturn.add(item);
        }
        return toReturn;
    }
}
