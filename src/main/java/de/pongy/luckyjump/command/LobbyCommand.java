package de.pongy.luckyjump.command;

import de.pongy.luckyjump.LuckyJump;
import de.pongy.luckyjump.config.LobbyConfig;
import de.pongy.luckyjump.utils.Messages;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * lobby setspawn
 */
public class LobbyCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Messages.mustPlayer);
            return true;
        }
        Player player = (Player) sender;
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("setspawn")) {
                LobbyConfig cfg = LuckyJump.getInstance().lobbyConfig;
                cfg.saveLocation("spawn", player.getLocation());
                sender.sendMessage("The Lobby-Spawn have been updated!");
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 10);
            } else
                sendHelp(sender);
        } else
            sendHelp(sender);
        return true;
    }
    public void sendHelp(CommandSender sender) {
        sender.sendMessage(ChatColor.GRAY + "Available Commands for Lobby Command:");
        sender.sendMessage(ChatColor.GRAY + "/lobby setspawn --> sets the spawn of the lobby to your actual location");
        sender.sendMessage(ChatColor.RED + "Visit LuckyJump/LobbyConfig.yml to configure more!");
    }
}
