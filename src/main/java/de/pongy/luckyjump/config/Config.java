package de.pongy.luckyjump.config;

import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public abstract class Config {

    private String dir, filename;

    public Config(String dir, String filename) {
        this.dir = dir;
        this.filename = filename;
    }
    public File getFile() {
        return new File(dir, filename);
    }
    public YamlConfiguration getConfiguration() {
        return YamlConfiguration.loadConfiguration(getFile());
    }
    public void saveConfig(YamlConfiguration cfg) {
        try {
            cfg.save(getFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void saveLocation(String node, Location location) {
        YamlConfiguration cfg = getConfiguration();

        if (false) {
            cfg.set(node + ".x", location.getX());
            cfg.set(node + ".y", location.getY());
            cfg.set(node + ".z", location.getZ());
            cfg.set(node + ".pitch", location.getPitch());
            cfg.set(node + ".yaw", location.getYaw());
            cfg.set(node + ".world", location.getWorld().getName());
        }
        cfg.set(node, location);

        saveConfig(cfg);
    }
    public Location loadLocation(String node) {
        YamlConfiguration cfg = getConfiguration();
        if (cfg.isLocation(node)) {
            return cfg.getLocation(node);
        }
        return null;
    }
    public abstract void setDefaults();
    public abstract  void loadDefaults();
}
