package de.pongy.luckyjump.specialitems.unluckyitems;

import de.pongy.luckyjump.LuckyJump;
import de.pongy.luckyjump.specialitems.LuckyItem;
import de.pongy.luckyjump.specialitems.luckyitems.FreezeEnemyItem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class FreezyYourself extends LuckyItem {
    public FreezyYourself() {
        super("Freeze", false);
    }

    @Override
    public void giveItem(Player player) {
        super.giveItem(player);
        if (!FreezeEnemyItem.freezedPlayers.contains(player)) {
            FreezeEnemyItem.freezedPlayers.add(player);
            Bukkit.getScheduler().runTaskLater(LuckyJump.getInstance(), new Runnable() {
                @Override
                public void run() {
                    FreezeEnemyItem.freezedPlayers.remove(player);
                }
            }, 20*5);
        }
    }
}
