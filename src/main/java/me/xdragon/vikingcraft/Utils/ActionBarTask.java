package me.xdragon.vikingcraft.Utils;

import me.xdragon.vikingcraft.Main;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class ActionBarTask extends BukkitRunnable {

    private final JavaPlugin plugin;

    public ActionBarTask(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        for(Player p: Bukkit.getOnlinePlayers()){
            p.sendActionBar(Component.text(Utils.Chat("&c" + String.valueOf(Main.playerStats.getStat(p.getUniqueId(), statNames.HEALTH)) + " ❤")));
        }
    }
}
