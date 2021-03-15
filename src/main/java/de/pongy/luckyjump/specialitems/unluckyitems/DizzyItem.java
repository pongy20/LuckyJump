package de.pongy.luckyjump.specialitems.unluckyitems;

import de.pongy.luckyjump.specialitems.LuckyItem;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class DizzyItem extends LuckyItem {

    public DizzyItem() {
        super("dizzy", false);
    }

    @Override
    public void giveItem(Player player) {
        super.giveItem(player);
        player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 20*10, 2));
    }
}
