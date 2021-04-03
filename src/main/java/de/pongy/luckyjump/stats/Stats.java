package de.pongy.luckyjump.stats;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class Stats {

    private String playername;
    private int games;
    private int wins;
    private int jumps;
    private int checkpointsHit;
    private int fellOff;

    public Stats(String playername, int games, int wins, int checkpointsHit, int fellOff, int jumps) {
        this.playername = playername;
        this.games = games;
        this.wins = wins;
        this.checkpointsHit = checkpointsHit;
        this.fellOff = fellOff;
        this.jumps = jumps;
    }

    public void printStats(CommandSender target) {
        target.sendMessage(ChatColor.RED + "Stats von " + ChatColor.GOLD + playername);
        target.sendMessage(ChatColor.YELLOW + "Gespielte Spiele: " + ChatColor.GOLD + games);
        target.sendMessage(ChatColor.YELLOW + "Gewonnene Spiele: " + ChatColor.GOLD + wins);
        target.sendMessage(ChatColor.YELLOW + "Anzahl der Sprünge: " + ChatColor.GOLD + jumps);
        target.sendMessage(ChatColor.YELLOW + "Checkpoints erreicht: " + ChatColor.GOLD + checkpointsHit);
        target.sendMessage(ChatColor.YELLOW + "Runtergefallen: " + ChatColor.GOLD + fellOff);
    }

    public String getPlayername() {
        return playername;
    }

    public int getGames() {
        return games;
    }

    public void addGame() {
        this.games += 1;
    }

    public int getWins() {
        return wins;
    }

    public void addWin() {
        this.wins += 1;
    }

    public int getCheckpointsHit() {
        return checkpointsHit;
    }

    public void addCheckpointHit() {
        this.checkpointsHit += 1;
    }

    public int getFellOff() {
        return fellOff;
    }

    public void addFellOff() {
        this.fellOff += 1;
    }

    public int getJumps() {
        return jumps;
    }

    public void addJump() {
        this.jumps += 1;
    }
}
