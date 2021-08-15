package de.pongy.luckyjump.utils;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;
import java.util.List;

public class Hologram {

    public static List<Hologram> holograms = new ArrayList<>();

    public static boolean doesHologramExists(String name) {
        for (Hologram holo : holograms) {
            if (holo.name.equals(name))
                return true;
        }
        return false;
    }
    public static Hologram getHologram(String name) {
        for (Hologram holo : holograms) {
            if (holo.name.equals(name))
                return holo;
        }
        return null;
    }

    public String name;
    public List<String> lines;
    public Location loc;
    public List<ArmorStand> armorStands;
    private double lineSpace;
    public boolean isSpawned;

    public Hologram(String name, Location loc) {
        this.name = name;
        this.loc = loc;
        lines = new ArrayList<>();
        armorStands = new ArrayList<>();
        lineSpace = 0.35;
        isSpawned = false;

        holograms.add(this);
    }

    public void spawn() {
        if (isSpawned)
            return;
        double backupY = loc.getY();
        for (int i = 0; i < lines.size(); i++) {
            ArmorStand stand = (ArmorStand) loc.getWorld().spawnEntity(loc.subtract(0, lineSpace, 0), EntityType.ARMOR_STAND);
            stand.setVisible(false);
            stand.setCustomNameVisible(true);
            stand.setGravity(false);
            stand.setCustomName(lines.get(i));
            armorStands.add(stand);
        }
        loc.setY(backupY);
        isSpawned = true;
    }
    public void despawn() {
        if (!isSpawned)
            return;
        for (ArmorStand stand : armorStands) {
            stand.remove();
        }
        isSpawned = false;
    }
    public void setLineSpace(double lineSpace) {
        this.lineSpace = lineSpace;
    }
    public double getLineSpace() {
        return lineSpace;
    }
    public void setLines(List<String> lines) {
        this.lines = lines;
    }
    public void addLine(String line) {
        lines.add(line);
    }
    public void removeLine(int index) {
        lines.remove(index);
    }

}
