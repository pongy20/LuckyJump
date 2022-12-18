package de.pongy.luckyjump.stats;

import de.pongy.luckyjump.sql.SQL;
import de.pongy.luckyjump.utils.UUIDConverter;
import org.bukkit.Bukkit;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 *  Table Structure: LJStats
 *  uuid VARCHAR(40) NOT NULL PRIMARY KEY
 *  games INTEGER NOT NULL
 *  wins INTEGER NOT NULL
 *  jumps INTEGER NOT NULL
 *  checkpointsHit INTEGER NOT NULL
 *  fellOff INTEGER NOT NULL
 */
public class StatsSQL {

    private static StatsSQL instance;

    public static synchronized StatsSQL getInstance() {
        if (instance == null)
            instance = new StatsSQL();
        return instance;
    }

    private final String TABLE_NAME = "LJStats";

    public SQL sql;

    private StatsSQL() {
        this.sql = SQL.getInstance();
    }

    public void createTable() {
        try {
            PreparedStatement ps = sql.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " " +
                    "(uuid VARCHAR(40) NOT NULL," +
                    "games INTEGER NOT NULL," +
                    "wins INTEGER NOT NULL," +
                    "jumps INTEGER NOT NULL," +
                    "checkpointsHit INTEGER NOT NULL," +
                    "fellOff INTEGER NOT NULL," +
                    "CONSTRAINT pk_stats PRIMARY KEY(uuid))");
            ps.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
    public void insertPlayer(String uuid) {
        if (doesPlayerExists(uuid)) {
            return;
        }
        try {
            PreparedStatement statement = sql.getConnection().prepareStatement("INSERT INTO " + TABLE_NAME
                    + " (uuid,games,wins,jumps,checkpointsHit,fellOff) VALUES (?,?,?,?,?,?)");
            statement.setString(1, uuid);
            statement.setInt(2, 0);
            statement.setInt(3, 0);
            statement.setInt(4, 0);
            statement.setInt(5, 0);
            statement.setInt(6, 0);
            statement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
    public void updatePlayer(String uuid, Stats stats) {
        if (!doesPlayerExists(uuid)) {
            insertPlayer(uuid);
        }
        try {
            PreparedStatement statement = sql.getConnection().prepareStatement("UPDATE " + TABLE_NAME
                    + " SET games = ?, wins = ?, jumps = ?, checkpointsHit = ?, fellOff = ? WHERE uuid = ?");
            statement.setInt(1, stats.getGames());
            statement.setInt(2, stats.getWins());
            statement.setInt(3, stats.getJumps());
            statement.setInt(4, stats.getCheckpointsHit());
            statement.setInt(5, stats.getFellOff());
            statement.setString(6, uuid);
            statement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
    public Stats getStats(String uuid) {
        if (!doesPlayerExists(uuid))
            return null;
        try {
            PreparedStatement statement = sql.getConnection().prepareStatement(
                    "SELECT games, wins, jumps, checkpointsHit, fellOff FROM " + TABLE_NAME + " WHERE uuid = ?");
            statement.setString(1, uuid);
            ResultSet result = statement.executeQuery();
            while(result.next()) {
                String playername = Bukkit.getOfflinePlayer(UUID.fromString(uuid)).getName();
                int games = result.getInt("games");
                int wins = result.getInt("wins");
                int jumps = result.getInt("jumps");
                int checkpointsHit = result.getInt("checkpointsHit");
                int fellOff = result.getInt("fellOff");
                return new Stats(playername, games, wins, checkpointsHit, fellOff, jumps);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }
    /**
     * Gets the Stats by given category
     * @param limit the limit of values wich should be selected to save database power
     * @return a TreeMap cause the stats are sorted and have to be accessed in a sorted way
     */
    public TreeMap<String, Integer> getStats(StatsCategory category, int limit) {
        TreeMap<String, Integer> stats = new TreeMap<>();
        try {
            PreparedStatement ps = sql.getConnection().prepareStatement("SELECT uuid," + category.getSqlName() + " FROM " + TABLE_NAME +
                    " ORDER BY " + category.getSqlName() + " DESC LIMIT " + limit);
            ResultSet result = ps.executeQuery();
            while (result.next()) {
                String playername = UUIDConverter.getInstance().getUsernameByUUID(result.getString("uuid"));
                int value = result.getInt(category.getSqlName());
                stats.put(playername, value);
            }
            return stats;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    public boolean doesPlayerExists(String uuid) {
        try {
            PreparedStatement statement = sql.getConnection().prepareStatement("SELECT games FROM " + TABLE_NAME + " WHERE uuid = ?");
            statement.setString(1, uuid);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return false;
    }
}
