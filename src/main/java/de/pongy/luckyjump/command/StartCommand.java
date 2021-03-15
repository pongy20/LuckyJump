package de.pongy.luckyjump.command;

import de.pongy.luckyjump.LuckyJump;
import de.pongy.luckyjump.config.LobbyConfig;
import de.pongy.luckyjump.game.Lobby;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class StartCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (LuckyJump.getInstance().actualPhase instanceof Lobby) {
            Lobby lobby = LuckyJump.getInstance().lobby;
            if (args.length == 0) {
                if (lobby.countdown.getTime() > LobbyConfig.startCommandTime)
                    lobby.countdown.setTime(LobbyConfig.startCommandTime);
                sender.sendMessage(ChatColor.GREEN + "You forced the game to start!");
            } else if (args.length == 1) {
                int time;
                try {
                    time = Integer.parseInt(args[0]);
                } catch (NumberFormatException ex) {
                    sender.sendMessage(ChatColor.RED + args[0] + " is not a valid number!");
                    sender.sendMessage(ChatColor.RED + "Please enter a valid number of seconds!");
                    return true;
                }
                lobby.countdown.setTime(time);
                sender.sendMessage(ChatColor.GREEN + "You updated the lobby time to " + ChatColor.GOLD + time + ChatColor.GREEN + " seconds!");
            } else {
                sender.sendMessage(ChatColor.GRAY + "/start <seconds>");
            }
        }

        return true;
    }
}
