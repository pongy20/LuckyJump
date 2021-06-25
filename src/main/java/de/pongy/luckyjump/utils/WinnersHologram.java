package de.pongy.luckyjump.utils;

import de.pongy.api.essentials.hologram.Hologram;
import de.pongy.luckyjump.stats.StatsCategory;
import de.pongy.luckyjump.stats.StatsService;
import org.bukkit.ChatColor;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;
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
        if (wins == null || wins.isEmpty()) {
            System.out.println("Unable to load wins!");
            return;
        }
        int index = 1;
        addLine(ChatColor.RED + "Beste Spieler (Wins):");
        addLine(" ");
        List<Map.Entry<String, Integer>> sortedResult = new ArrayList<>(wins.entrySet());
        sortedResult.sort(Map.Entry.comparingByValue());
        for (int i = sortedResult.size() - 1; i >= 0; i--) {
            addLine(ChatColor.AQUA.toString() + index + ". " + sortedResult.get(i).getKey() + ChatColor.GOLD + " " + sortedResult.get(i).getValue());
            index+=1;
        }
        spawn();
    }
}
