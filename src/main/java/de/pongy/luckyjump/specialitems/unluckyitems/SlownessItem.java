package de.pongy.luckyjump.specialitems.unluckyitems;

import de.pongy.luckyjump.specialitems.LuckyItem;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SlownessItem extends LuckyItem {
    public SlownessItem() {
        super("Slowness", false);
    }

    @Override
    public void giveItem(Player player) {
        super.giveItem(player);
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20*6, 1));
    }
}
