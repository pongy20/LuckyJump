package de.pongy.luckyjump.specialitems.luckyitems;

import de.pongy.luckyjump.LuckyJump;
import de.pongy.luckyjump.game.Game;
import de.pongy.luckyjump.game.LuckyJumpPlayer;
import de.pongy.luckyjump.specialitems.LuckyItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class KillRod extends LuckyItem implements Listener {
    public KillRod() {
        super("Kill Rod", true);
    }

    @Override
    public void giveItem(Player player) {
        super.giveItem(player);
        ItemStack blazeRod = new ItemStack(Material.BLAZE_ROD);
        ItemMeta meta = blazeRod.getItemMeta();;
        meta.setDisplayName(ChatColor.RED + "Click to kill your enemy!");
        blazeRod.setItemMeta(meta);

        player.getInventory().addItem(blazeRod);
    }
    @EventHandler
    public void onInteractRod(PlayerInteractEvent event) {
        if (LuckyJump.getInstance().actualPhase instanceof Game) {
            Player player = event.getPlayer();
            if (player.getInventory().getItemInMainHand().getType().equals(Material.BLAZE_ROD)) {
                doKill(player);
                ItemStack newItem = player.getInventory().getItemInMainHand();
                newItem.setAmount(newItem.getAmount()-1);
                player.getInventory().setItemInMainHand(newItem);
            }
        }
    }
    private void doKill(Player sender) {
        Game game = LuckyJump.getInstance().game;
        LuckyJumpPlayer lSender = game.getPlayer(sender);
        LuckyJumpPlayer toKill = lSender.equals(game.playerA) ? game.playerB : game.playerA;
        game.sendBackToCheckpoint(toKill.getPlayer());
        toKill.sendMessage(ChatColor.RED + "Your opponent killed you with " + name);
        toKill.getPlayer().playSound(toKill.getPlayer().getLocation(), Sound.ENTITY_VILLAGER_HURT, 10,10);
        sender.sendMessage(ChatColor.GREEN + "You killed your opponent!");
        sender.playSound(sender.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 10);
    }
}
