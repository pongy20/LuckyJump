package de.pongy.luckyjump.specialitems.luckyitems;

import de.pongy.luckyjump.specialitems.LuckyItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class BowItem extends LuckyItem {

    public BowItem() {
        super("Der ultimative Bogen", true);
    }

    @Override
    public void giveItem(Player player) {
        super.giveItem(player);
        ItemStack bow = new ItemStack(Material.BOW);
        ItemMeta meta = bow.getItemMeta();
        meta.setDisplayName(ChatColor.LIGHT_PURPLE + name);

        bow.setItemMeta(meta);
        bow.addEnchantment(Enchantment.ARROW_KNOCKBACK, 2);
        ItemStack arrow = new ItemStack(Material.ARROW);
        player.getInventory().addItem(bow);
        player.getInventory().addItem(arrow);
    }
}
