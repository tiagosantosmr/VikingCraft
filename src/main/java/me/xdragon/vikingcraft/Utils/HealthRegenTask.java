package me.xdragon.vikingcraft.Utils;

import me.xdragon.vikingcraft.Main;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class HealthRegenTask extends BukkitRunnable {

    private final JavaPlugin plugin;

    public HealthRegenTask(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        for(Player p: Bukkit.getOnlinePlayers()){ //regens the health of all the players
            p.sendMessage(Main.playerStats.getStats(p.getUniqueId()).toString());
            Main.playerStats.setStat(p.getUniqueId(),
                    statNames.HEALTH,
                    Math.min(
                            Main.playerStats.getMaxBonusHealth(p.getUniqueId()),
                            Main.playerStats.getStat(p.getUniqueId(), statNames.HEALTHREGEN) + Main.BASEHEALTHREGEN + Main.playerStats.getStat(p.getUniqueId(), statNames.HEALTH)
                    )
            );

        }
    }

}
