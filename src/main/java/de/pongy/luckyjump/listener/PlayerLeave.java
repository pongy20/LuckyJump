package de.pongy.luckyjump.listener;

import de.pongy.luckyjump.LuckyJump;
import de.pongy.luckyjump.game.Game;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;

public class PlayerLeave  implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (LuckyJump.getInstance().actualPhase != null && LuckyJump.getInstance().actualPhase.containsPlayer(event.getPlayer())) {
            LuckyJump.getInstance().actualPhase.removePlayer(event.getPlayer());

            if (LuckyJump.getInstance().actualPhase instanceof Game) {      // check if the other player won
                Game game = LuckyJump.getInstance().game;
                if (game.ended) {   // game already ended and existing players just get kicked of server
                    return;
                }
                Player winner;
                if (game.playerA.getPlayer().getName().equalsIgnoreCase(player.getName())) {
                    winner = game.playerA.getPlayer();
                } else {
                    winner = game.playerB.getPlayer();
                }
                game.winGame(winner);
            }
        }
        // remove potion effects
        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
        event.setQuitMessage(null);
    }

}
