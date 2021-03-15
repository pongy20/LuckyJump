package de.pongy.luckyjump.game;

import de.pongy.luckyjump.utils.Messages;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class GamePhase {

    protected final Location spawn;
    public List<LuckyJumpPlayer> players;
    protected List<Player> spectators; // will be added later on

    public GamePhase(Location spawn) {
        this.spawn = spawn;
        players  = new ArrayList<>();
        spectators = new ArrayList<>();
    }

    public void sendGameMessage(String message) {
        for (LuckyJumpPlayer player : players) {
            player.getPlayer().sendMessage(Messages.prefix + message);
        }
        for (Player spec : spectators) {
            spec.sendMessage(Messages.prefix + message);
        }
    }
    public boolean containsPlayer(Player player) {
        for (LuckyJumpPlayer all : players) {
            if (all.getPlayer().getName().equals(player.getName()))
                return true;
        }
        return false;
    }
    public void setPlayers (List<LuckyJumpPlayer> players) {
        this.players = players;
    }
    public void addPlayer(LuckyJumpPlayer player) {
        players.add(player);
    }
    public void removePlayer(Player player) {
        LuckyJumpPlayer toDelete = null;        // to avoid concurrentModificationException in this case
        for (LuckyJumpPlayer all : players) {
            if (all.getPlayer().getName().equals(player.getName())) {
                toDelete = all;
            }
        }
        players.remove(toDelete);
    }
    public Location getSpawn() {
        return spawn;
    }

}
