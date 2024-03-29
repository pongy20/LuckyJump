package de.pongy.luckyjump.specialitems.unluckyitems;

import de.pongy.luckyjump.LuckyJump;
import de.pongy.luckyjump.game.Game;
import de.pongy.luckyjump.specialitems.LuckyItem;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class BackToCheckpoint extends LuckyItem {

    public BackToCheckpoint() {
        super("back to checkpoint", false);
    }

    @Override
    public void giveItem(Player player) {
        player.sendMessage(ChatColor.RED + "You got unlucky!");
        player.sendMessage(ChatColor.DARK_RED + "You've been teleported back to checkpoint.");
        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 10, 10);
        Game game = LuckyJump.getInstance().game;
        game.sendBackToCheckpoint(player);
    }
}
