package de.pongy.luckyjump.utils;

import de.pongy.api.essentials.hologram.Hologram;
import de.pongy.luckyjump.stats.StatsCategory;
import de.pongy.luckyjump.stats.StatsService;
import org.bukkit.ChatColor;
import org.bukkit.Location;

import java.util.Map;
import java.util.TreeMap;

public class WinnersHologram extends Hologram {

    public WinnersHologram(Location loc) {
        super("WinnersHologram", loc);
    }

    public void updateLines() {
        despawn();
        lines.clear();
        TreeMap<String, Integer> wins = StatsService.getInstance().getBestStats(StatsCategory.WINS, 5);
        int index = 1;
        addLine(ChatColor.RED + "Best Players: Wins");
        addLine("");
        for (Map.Entry<String, Integer> pair : wins.entrySet()) {
            addLine(ChatColor.AQUA.toString() + index + ". " +  pair.getKey() + ChatColor.GOLD + " " + pair.getValue());
            index+=1;
        }
        spawn();
    }
}
