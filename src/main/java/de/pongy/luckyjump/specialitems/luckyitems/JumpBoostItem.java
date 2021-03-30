package de.pongy.luckyjump.specialitems.luckyitems;

import de.pongy.luckyjump.specialitems.LuckyItem;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class JumpBoostItem extends LuckyItem {
    public JumpBoostItem() {
        super("Jump Boost", true);
    }

    @Override
    public void giveItem(Player player) {
        super.giveItem(player);
        player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 20*7, 1));
    }
}
