package de.pongy.luckyjump.bungee;

import de.pongy.luckyjump.LuckyJump;
import de.pongy.luckyjump.config.GameConfig;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

public class BungeeConnector {

    public static void sendPlayerBackToHub(Player p) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF(GameConfig.hubServerName);

        p.sendMessage("Connecting to hub...");
        p.sendPluginMessage(LuckyJump.getInstance(), "BungeeCord", out.toByteArray());

    }

}
