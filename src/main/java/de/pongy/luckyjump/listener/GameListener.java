package de.pongy.luckyjump.listener;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import de.pongy.luckyjump.LuckyJump;
import de.pongy.luckyjump.game.Game;
import de.pongy.luckyjump.game.LuckyJumpPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

public class GameListener implements Listener {

    /**
     * Used to check falling and checkpoints went on
     */
    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if (!(LuckyJump.getInstance().actualPhase instanceof Game)) {
            return;
        }
        Game game = LuckyJump.getInstance().game;
        Player player = event.getPlayer();
        // catch fall out of map
        if (player.getLocation().getBlockY() <= game.map.getResetY()) {
            game.sendBackToCheckpoint(player);
            return;
        }
        // catch checkpoint
        if (player.getLocation().getBlock().getType().equals(Game.checkpointMaterial)) {
            game.updateCheckpoint(player, player.getLocation());
            return;
        }
        // catch win
        if (player.getLocation().getBlock().getType().equals(Game.winCheckpointMaterial)) {
            game.winGame(player);
        }
    }
    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (!(LuckyJump.getInstance().actualPhase instanceof Game)) {
            event.setCancelled(true);
            return;
        }
        // catch fall damage during game
        if (event.getEntity() instanceof Player && event.getCause().equals(EntityDamageEvent.DamageCause.FALL)) {
            event.setCancelled(true);
        }
        if (event.getEntity() instanceof Player && event.getDamage() > ((Player) event.getEntity()).getHealth()) {
            Player player = (Player) event.getEntity();
            player.setHealth(20);
            player.setFoodLevel(20);
            LuckyJump.getInstance().game.sendBackToCheckpoint(player);
            event.setDamage(0);
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onLuckyBowShot(EntityShootBowEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }
        Player player = (Player) event.getEntity();
        if (player.getInventory().getItemInMainHand().equals(event.getBow()))
            player.getInventory().setItemInMainHand(null);
        else
            player.getInventory().setItemInOffHand(null);
    }
    @EventHandler
    public void onJump(PlayerJumpEvent event) {
        if (LuckyJump.getInstance().game instanceof Game) {
            Game game = LuckyJump.getInstance().game;
            game.getPlayer(event.getPlayer()).stats.addJump();
        }
    }
    @EventHandler
    public void onWeatherChange(WeatherChangeEvent event) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "weather clear");
        event.getWorld().setClearWeatherDuration(Integer.MAX_VALUE);
        event.getWorld().setWeatherDuration(Integer.MAX_VALUE);
    }
    @EventHandler
    public void onMobSpawn(EntitySpawnEvent event) {
        if (!(event.getEntity() instanceof Projectile))
            event.setCancelled(true);
    }
    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if (LuckyJump.getInstance().actualPhase != null)
            event.setCancelled(true);
    }
    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        if (LuckyJump.getInstance().actualPhase != null)
            event.setCancelled(true);
    }
    @EventHandler
    public void onFoodLevel(FoodLevelChangeEvent event) {
        event.setFoodLevel(20);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        event.getEntity().teleport(LuckyJump.getInstance().lobby.getSpawn());
        System.out.println("Test");
    }
}
