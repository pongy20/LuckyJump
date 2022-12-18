package de.pongy.luckyjump.command;

import de.pongy.luckyjump.LuckyJump;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class HologramCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("clear")) {
                if (LuckyJump.getInstance().game != null) {
                    for (Entity e : LuckyJump.getInstance().game.map.getWorld().getEntities()) {
                        if (e instanceof ArmorStand) {
                            e.remove();
                        }
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "Can't load game world. You can manually remove the holograms by using the command:");
                    sender.sendMessage(ChatColor.RED + "/kill @e");
                    sender.sendMessage(ChatColor.RED + "Attention!: All entity in the given world will be removed using that command.");
                }
            } else {
                sendHelp(sender);
            }
        } else {
            sendHelp(sender);
        }
        return true;
    }

    private void sendHelp(CommandSender sender) {
        sender.sendMessage(ChatColor.GRAY + "/hologram clear - clears all holograms");
    }

}
