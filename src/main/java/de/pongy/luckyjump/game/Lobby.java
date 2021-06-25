package de.pongy.luckyjump.game;

import de.pongy.luckyjump.LuckyJump;
import de.pongy.luckyjump.config.LobbyConfig;
import de.pongy.luckyjump.utils.Countdown;
import de.pongy.luckyjump.utils.LuckyJumpAction;
import de.pongy.luckyjump.utils.WinnersHologram;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;

public class Lobby extends GamePhase {

    public static int maxPlayers = 2;
    public static int minPlayers = 2;

    public Countdown countdown;
    public WinnersHologram winnersHologram;

    public Lobby(Location spawn) {
        super(spawn);
        countdown = new Countdown(LobbyConfig.lobbyTime, getEndAction());
        winnersHologram = new WinnersHologram(LobbyConfig.winnersHologramLocation);
        winnersHologram.updateLines();
    }
    @Override
    public void addPlayer(LuckyJumpPlayer player) {
        if (players.contains(player))
            return;
        if (players.size() > maxPlayers) {
            player.getPlayer().kickPlayer("Spiel ist voll!"); //TODO: have to be replaced with sending back to lobby
            return;
        }
        super.addPlayer(player);
        sendGameMessage(ChatColor.GREEN + player.getPlayer().getName() + " hat das Spiel betreten (" + players.size() + "/" + maxPlayers + ").");
        player.getPlayer().teleport(spawn);
        player.clearInventory();
        player.setLevel(0);
        player.getPlayer().setGameMode(GameMode.SURVIVAL);
        triggerGameStart();
    }

    @Override
    public void removePlayer(Player player) {
        super.removePlayer(player);
        sendGameMessage(ChatColor.RED + player.getName() + " hat das Spiel verlassen (" + players.size() + "/" + maxPlayers + ").");
        if (players.isEmpty()) {
            countdown.pause();
            countdown.setTime(30);
        }
    }

    private void triggerGameStart() {
        if (players.size() >= minPlayers) {      // start countdown condition
            countdown.start();
        } else {
            sendGameMessage(ChatColor.GREEN + "Es müssen mindestens " + ChatColor.GOLD + minPlayers + ChatColor.GREEN + " Spieler online sein, um das Spiel zu starten!");
        }
    }
    public LuckyJumpAction getEndAction() {
        return new LuckyJumpAction() {
            @Override
            public void doAction() {
                if (players.size() >= minPlayers) {
                    LuckyJump main = LuckyJump.getInstance();
                    main.actualPhase = main.game;
                    main.game.setPlayers(players);
                    main.game.startGame();
                } else {
                    countdown.setTime(10);
                    countdown.start();
                }
            }
        };
    }
}
