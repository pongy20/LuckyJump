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
                sender.sendMessage(ChatColor.GREEN + "Du hast den Spielstart forciert!");
            } else if (args.length == 1) {
                int time;
                try {
                    time = Integer.parseInt(args[0]);
                } catch (NumberFormatException ex) {
                    sender.sendMessage(ChatColor.RED + args[0] + " ist keine gültige Nummer!");
                    sender.sendMessage(ChatColor.RED + "Bitte gebe eine gültige Nummer ein!");
                    return true;
                }
                lobby.countdown.setTime(time);
                sender.sendMessage(ChatColor.GREEN + "Du hast die Zeit bis zum Spielstart auf " + ChatColor.GOLD + time + ChatColor.GREEN + " Sekunden geändert!");
            } else {
                sender.sendMessage(ChatColor.GRAY + "/start <seconds>");
            }
        }

        return true;
    }
}
