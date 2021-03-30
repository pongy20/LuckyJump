package de.pongy.luckyjump.stats;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

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
        System.out.println(Bukkit.getOfflinePlayer(playername).getUniqueId().toString());
        if (stats != null)
            return stats;
        else {
            return new Stats(playername, 0, 0, 0, 0, 0);
        }
    }

    /**
     * Pushes the stats to database
     */
    public void pushStats(OfflinePlayer player, Stats stats) {
        StatsSQL.getInstance().updatePlayer(player.getUniqueId().toString(), stats);        // this auto-insert if the player don't exists
    }

}
