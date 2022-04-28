package me.xdragon.vikingcraft.Listeners;

import me.xdragon.vikingcraft.Main;
import me.xdragon.vikingcraft.Utils.Utils;
import me.xdragon.vikingcraft.Utils.statNames;
import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    public Main plugin;

    public PlayerJoinListener(Main plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        if(!player.hasPlayedBefore()) {
            Main.playerStats.addPlayer(player.getUniqueId());
        }
        Utils.updateScoreboard(player);
        player.sendActionBar(Component.text(Utils.Chat("&c" + String.valueOf(Main.playerStats.getStat(player.getUniqueId(), statNames.HEALTH)) + " ❤")));
    }

}

