package de.pongy.luckyjump.game;

import de.pongy.luckyjump.LuckyJump;
import de.pongy.luckyjump.config.GameConfig;
import de.pongy.luckyjump.config.LobbyConfig;
import de.pongy.luckyjump.language.LanguageConfig;
import de.pongy.luckyjump.language.LanguagePlaceholder;
import de.pongy.luckyjump.language.MessageKeys;
import de.pongy.luckyjump.language.PlaceholderPrefabs;
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
        if (GameConfig.useStats) {
            winnersHologram = new WinnersHologram(LobbyConfig.winnersHologramLocation);
            winnersHologram.updateLines();
        }
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

        LanguagePlaceholder playerPlaceholder = new LanguagePlaceholder(PlaceholderPrefabs.PLAYER.getName(), player.getPlayer().getName());
        LanguagePlaceholder currentPlaceholder = new LanguagePlaceholder(PlaceholderPrefabs.CURRENT_PLAYER.getName(), players.size() + "");
        LanguagePlaceholder maxPlaceholder = new LanguagePlaceholder(PlaceholderPrefabs.MAX_PLAYER.getName(), maxPlayers + "");

        sendGameMessage(LanguageConfig.getInstance().getMessage(MessageKeys.PLAYER_JOIN.getKey(),
                playerPlaceholder));
        player.getPlayer().teleport(spawn);
        player.clearInventory();
        player.setLevel(0);
        player.getPlayer().setGameMode(GameMode.SURVIVAL);
        triggerGameStart();
    }

    @Override
    public void removePlayer(Player player) {
        super.removePlayer(player);

        LanguagePlaceholder playerPlaceholder = new LanguagePlaceholder(PlaceholderPrefabs.PLAYER.getName(), player.getPlayer().getName());
        LanguagePlaceholder currentPlaceholder = new LanguagePlaceholder(PlaceholderPrefabs.CURRENT_PLAYER.getName(), players.size() + "");
        LanguagePlaceholder maxPlaceholder = new LanguagePlaceholder(PlaceholderPrefabs.MAX_PLAYER.getName(), maxPlayers + "");

        sendGameMessage(LanguageConfig.getInstance().getMessage(MessageKeys.PLAYER_LEFT.getKey(),
                playerPlaceholder,currentPlaceholder,maxPlaceholder));
        if (players.isEmpty()) {
            countdown.pause();
            countdown.setTime(30);
        }
    }

    private void triggerGameStart() {
        if (players.size() >= minPlayers) {      // start countdown condition
            countdown.start();
        } else {
            LanguagePlaceholder minPlaceholder = new LanguagePlaceholder(PlaceholderPrefabs.MIN_PLAYER.getName(), minPlayers + "");
            sendGameMessage(LanguageConfig.getInstance().getMessage(MessageKeys.MINIMUM_PLAYERS_INGAME.getKey(), minPlaceholder));
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
