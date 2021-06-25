package de.pongy.luckyjump.command;

import de.pongy.luckyjump.stats.Stats;
import de.pongy.luckyjump.stats.StatsService;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

/**
 * stats --> display own stats
 * stats <player-name> display other stats
 */
public class StatsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (args.length == 0) {
            showStats(sender, sender.getName());
        } else {
            showStats(sender, args[0]);
        }

        return true;
    }
    private void showStats(CommandSender sender, String targetPlayer) {
        Stats stats = StatsService.getInstance().getStats(targetPlayer);
        if (stats == null || stats.getGames() <= 0) {
            sender.sendMessage(ChatColor.GRAY + "Es konnten keine Stats für den Spieler " + ChatColor.GOLD + targetPlayer + ChatColor.GRAY + " gefunden werden.");
            return;
        }
        stats.printStats(sender);
    }
}
