package me.xdragon.vikingcraft.Listeners;

import io.papermc.paper.event.player.AsyncChatEvent;
import me.xdragon.vikingcraft.Main;
import me.xdragon.vikingcraft.Utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ChatListener implements Listener {

    public ChatListener(Main plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onChat(AsyncChatEvent event){

    }

}
