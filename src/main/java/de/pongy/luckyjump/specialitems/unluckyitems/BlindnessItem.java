package de.pongy.luckyjump.specialitems.unluckyitems;

import de.pongy.luckyjump.specialitems.LuckyItem;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class BlindnessItem extends LuckyItem {

    public BlindnessItem() {
        super("Blindness", false);
    }

    @Override
    public void giveItem(Player player) {
        super.giveItem(player);
        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20*5, 2));
    }
}
