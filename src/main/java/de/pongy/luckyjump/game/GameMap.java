package de.pongy.luckyjump.game;

import org.bukkit.Location;
import org.bukkit.World;

public class GameMap {

    private final World world;
    private final Location spawnA, spawnB;
    private int resetY;     // will be the point a player have to reach to get back to last checkpoint

    public GameMap(World world, Location spawnA, Location spawnB) {
        this.world = world;
        this.spawnA = spawnA;
        this.spawnB = spawnB;
        resetY = 0;
    }

    public void setResetY(int resetY) {
        this.resetY = resetY;
    }

    public World getWorld() {
        return world;
    }

    public Location getSpawnA() {
        return spawnA;
    }

    public Location getSpawnB() {
        return spawnB;
    }

    public int getResetY() {
        return resetY;
    }
}
