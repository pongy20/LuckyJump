package de.pongy.luckyjump.listener;

import de.pongy.luckyjump.LuckyJump;
import de.pongy.luckyjump.config.LobbyConfig;
import de.pongy.luckyjump.game.Lobby;
import de.pongy.luckyjump.game.LuckyJumpPlayer;
import de.pongy.luckyjump.game.Spectator;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        event.setJoinMessage(null);
        if (LuckyJump.getInstance().actualPhase instanceof Lobby && LobbyConfig.autostart && LuckyJump.getInstance().actualPhase.players.size() < Lobby.maxPlayers) {
            LuckyJump.getInstance().lobby.addPlayer(new LuckyJumpPlayer(event.getPlayer()));
        } else {        // add spectator in this case
            Spectator.addPlayer(event.getPlayer());
        }
    }
}
