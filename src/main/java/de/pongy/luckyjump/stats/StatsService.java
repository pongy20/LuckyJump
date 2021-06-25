package de.pongy.luckyjump.stats;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.TreeMap;

public class StatsService {

    private static StatsService instance;

    public static StatsService getInstance() {
        if (instance == null)
            instance = new StatsService();
        return instance;
    }

    /**
     * Reads the stats from database and returns it
     */
    public Stats getStats(String playername) {
        Stats stats = StatsSQL.getInstance().getStats(Bukkit.getOfflinePlayer(playername).getUniqueId().toString());
        if (stats != null)
            return stats;
        else {
            return new Stats(playername, 0, 0, 0, 0, 0);
        }
    }

    /**
     *  Can be used to get the best players on servers in given category
     *  if a stat is null, there can't be find anyone
     * @param category select wich category should be selected
     * @param amount amount of Stats
     * @return a list of Stats wich are the best in given category
     */
    public TreeMap<String, Integer> getBestStats(StatsCategory category, int amount) {
        return StatsSQL.getInstance().getStats(category, amount);
    }

    /**
     * Pushes the stats to database
     */
    public void pushStats(OfflinePlayer player, Stats stats) {
        StatsSQL.getInstance().updatePlayer(player.getUniqueId().toString(), stats);        // this auto-insert if the player don't exists
    }

}
