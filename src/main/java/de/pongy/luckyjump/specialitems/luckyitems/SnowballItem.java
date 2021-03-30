package de.pongy.luckyjump.specialitems.luckyitems;

import de.pongy.luckyjump.specialitems.LuckyItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class SnowballItem extends LuckyItem {

    private final int MAX = 12;
    private final int MIN = 3;
    private Random rn;

    public SnowballItem() {
        super("Magic Snowballs", true);
        rn = new Random();
    }

    @Override
    public void giveItem(Player player) {
        super.giveItem(player);
        int snowballAmount = rn.nextInt(MAX - MIN) + 1 + MIN;
        player.getInventory().addItem(new ItemStack(Material.SNOWBALL, snowballAmount));
    }
}
