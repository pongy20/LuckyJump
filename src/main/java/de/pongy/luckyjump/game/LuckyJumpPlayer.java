package de.pongy.luckyjump.game;

import de.pongy.luckyjump.config.GameConfig;
import de.pongy.luckyjump.specialitems.LuckyItem;
import de.pongy.luckyjump.stats.Stats;
import de.pongy.luckyjump.stats.StatsService;
import de.pongy.luckyjump.utils.Messages;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import java.util.List;
import java.util.Random;

//TODO: maybe it is better to let this class extend Player
public class LuckyJumpPlayer {

    private final Player player;
    // recently hit checkpoint
    private Location checkpoint;
    // amount of coins the player earned through the game
    private int coins;
    public Stats stats;

    public LuckyJumpPlayer(Player player) {
        this.player = player;
        this.coins = 0;
        stats = StatsService.getInstance().getStats(player.getName());
    }

    /**
     * Sends a Message using the defined prefix
     * @param message Message to send
     */
    public void sendMessage(String message) {
        player.sendMessage(Messages.prefix + message);
    }
    public void sendActionBarMessage(String message) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(message));
    }
    public void clearInventory() {
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
    }
    public void setLevel(int level) {
        player.setLevel(level);
        player.setExp(0.0f);
    }
    public void clearPotionEffects() {
        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
    }
    public void sendCoinsAmount() {
        sendMessage(ChatColor.GREEN + "Du hast in dieser Runde " + coins + " coins verdient!");
    }
    public void makePlayerPlayable() {
        clearInventory();
        setLevel(0);
        player.setGameMode(GameMode.SURVIVAL);
        player.setAllowFlight(false);
    }
    public void doLuckyItem() {
        Random rn = new Random();
        if (rn.nextInt(100) + 1 < GameConfig.luckyPercentage) {     // lucky
            List<LuckyItem> luckyItems = LuckyItem.getLuckyItems();
            LuckyItem item = luckyItems.get(rn.nextInt(luckyItems.size()));
            item.giveItem(player);
        } else {                    // unlucky
            List<LuckyItem> unluckyItem = LuckyItem.getUnluckyItems();
            LuckyItem item = unluckyItem.get(rn.nextInt(unluckyItem.size()));
            item.giveItem(player);
        }
    }
    public Player getPlayer() {
        return player;
    }

    public Location getCheckpoint() {
        return checkpoint;
    }

    public void setCheckpoint(Location checkpoint) {
        this.checkpoint = checkpoint;
    }

    public int getCoins() {
        return coins;
    }

    public void addCoins(int coins) {
        this.coins += coins;
    }
}
