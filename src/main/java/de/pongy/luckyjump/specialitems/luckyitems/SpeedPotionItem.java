package de.pongy.luckyjump.specialitems.luckyitems;

import de.pongy.luckyjump.specialitems.LuckyItem;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SpeedPotionItem extends LuckyItem {
    public SpeedPotionItem() {
        super("Speed", true);
    }

    @Override
    public void giveItem(Player player) {
        super.giveItem(player);
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20*10, 0));
    }
}
