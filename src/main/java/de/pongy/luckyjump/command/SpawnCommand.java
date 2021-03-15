package de.pongy.luckyjump.command;

import de.pongy.luckyjump.LuckyJump;
import de.pongy.luckyjump.config.GameConfig;
import de.pongy.luckyjump.utils.Messages;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SpawnCommand implements CommandExecutor  {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Messages.mustPlayer);
            return true;
        }
        Player player = (Player) sender;
        if (args.length == 1) {
            GameConfig cfg = LuckyJump.getInstance().gameConfig;
            if (args[0].equalsIgnoreCase("1")) {
                cfg.saveLocation("spawn-player1", player.getLocation());
                sender.sendMessage(ChatColor.GREEN + "Spawn of player1 have been updated! Reload the server :)");
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 10);
            } else if (args[0].equalsIgnoreCase("2")) {
                cfg.saveLocation("spawn-player2", player.getLocation());
                sender.sendMessage(ChatColor.GREEN + "Spawn of player2 have been updated! Reload the server :)");
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 10);
            } else if (args[0].equalsIgnoreCase("spectator")) {
                cfg.saveLocation("spawn-spectator", player.getLocation());
                sender.sendMessage(ChatColor.GREEN + "Spawn of spectators have been updated! Reload the server :)");
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 10);
            } else
                sendHelp(sender);
        } else
            sendHelp(sender);
        return true;
    }
    public void sendHelp(CommandSender sender) {
        sender.sendMessage(ChatColor.GRAY + "Available Commands for setspawn Command:");
        sender.sendMessage(ChatColor.GRAY + "/setspawn 1 --> sets the spawn of the first player");
        sender.sendMessage(ChatColor.GRAY + "/setspawn 2 --> sets the spawn of the second player");
        sender.sendMessage(ChatColor.GRAY + "/setspawn spectator --> sets the spawn for the spectators");
    }
}
