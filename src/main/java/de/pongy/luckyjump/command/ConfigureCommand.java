package de.pongy.luckyjump.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ConfigureCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        sender.sendMessage(ChatColor.RED.toString() + ChatColor.UNDERLINE + "Steps to configure LuckyJump:");
        sender.sendMessage(ChatColor.RED + "1. Use /lobby setspawn to set the spawn of the lobby!");
        sender.sendMessage(ChatColor.RED + "2. Take a look on /plugins/LuckyJump/LobbyConfig.yml to configure more about the lobby!");
        sender.sendMessage(ChatColor.RED + "3. Use /setspawn 1 and setspawn 2 to set the two different spawn points!");
        sender.sendMessage(ChatColor.RED + "4. Use /setspawn spectator to set the location where spectators should spawn!");
        sender.sendMessage(ChatColor.RED + "5. Take a look on /plugins/LuckyJump/GameConfig.yml to configure more about the game!");
        sender.sendMessage(ChatColor.RED + "6. If you done all steps, you should reload/restart the server!");
        return true;
    }
}
