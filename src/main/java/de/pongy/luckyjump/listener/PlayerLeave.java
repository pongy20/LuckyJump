package de.pongy.luckyjump.listener;

import de.pongy.luckyjump.LuckyJump;
import de.pongy.luckyjump.game.LuckyJumpPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;

public class PlayerLeave  implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        if (LuckyJump.getInstance().actualPhase.containsPlayer(event.getPlayer())) {
            LuckyJump.getInstance().actualPhase.removePlayer(event.getPlayer());
        }
        Player player = event.getPlayer();
        // remove potion effects
        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
        event.setQuitMessage(null);
    }

}
