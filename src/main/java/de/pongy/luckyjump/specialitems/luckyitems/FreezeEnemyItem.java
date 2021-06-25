package de.pongy.luckyjump.specialitems.luckyitems;

import de.pongy.luckyjump.LuckyJump;
import de.pongy.luckyjump.game.Game;
import de.pongy.luckyjump.game.LuckyJumpPlayer;
import de.pongy.luckyjump.specialitems.LuckyItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class FreezeEnemyItem extends LuckyItem implements Listener {

    public static List<Player> freezedPlayers = new ArrayList<>();

    public FreezeEnemyItem() {
        super("Gegner einfrieren Item", true);
    }

    @Override
    public void giveItem(Player player) {
        super.giveItem(player);
        ItemStack freezeItem = new ItemStack(Material.ICE);
        ItemMeta meta = freezeItem.getItemMeta();
        meta.setDisplayName("Klicke um deinen Gegner einzufrieren!");
        freezeItem.setItemMeta(meta);
        player.getInventory().addItem(freezeItem);
    }
    @EventHandler
    public void onFreeze(PlayerInteractEvent e) {
        if (LuckyJump.getInstance().actualPhase instanceof Game) {
            Player player = e.getPlayer();
            if (player.getInventory().getItemInMainHand().getType().equals(Material.ICE)) {
                freezeEnemy(player);
                ItemStack newItem = player.getInventory().getItemInMainHand();
                newItem.setAmount(newItem.getAmount()-1);
                player.getInventory().setItemInMainHand(newItem);
            }
        }
    }
    @EventHandler
    public void onFrozenMove(PlayerMoveEvent event) {
        if (freezedPlayers.contains(event.getPlayer())) {
            event.setTo(event.getFrom());
        }
    }
    public void freezeEnemy(Player sender) {
        Game game = LuckyJump.getInstance().game;
        LuckyJumpPlayer lSender = game.getPlayer(sender);
        LuckyJumpPlayer toFreeze = lSender.equals(game.playerA) ? game.playerB : game.playerA;
        if (freezedPlayers.contains(toFreeze.getPlayer())) {
            sender.sendMessage(ChatColor.RED + "Dein Gegner ist bereits eingefroren!");
            return;
        }
        freezedPlayers.add(toFreeze.getPlayer());
        toFreeze.sendMessage(ChatColor.RED + "Dein Gegner hat dich eingefroren!");
        toFreeze.getPlayer().setAllowFlight(true);
        sender.sendMessage(ChatColor.GREEN + "Dein Gegner wurde eingefroren!");
        sender.playSound(sender.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 10);
        Bukkit.getScheduler().runTaskLater(LuckyJump.getInstance(), new Runnable() {
            @Override
            public void run() {
                freezedPlayers.remove(toFreeze.getPlayer());
                toFreeze.getPlayer().setAllowFlight(false);
            }
        }, 20*5);
    }
}
