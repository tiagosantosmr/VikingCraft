package me.xdragon.vikingcraft.Listeners;

import me.xdragon.vikingcraft.Main;
import me.xdragon.vikingcraft.Utils.Entities;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CreatureSpawnListener implements Listener {
    public Main plugin;

    List<Color> colors = new ArrayList<Color>(Arrays.asList(Color.WHITE, Color.AQUA, Color.BLUE, Color.RED, Color.GRAY, Color.BLACK));
    List<String> colors2 = new ArrayList<String>(Arrays.asList("&f", "&b", "&9", "&4", "&7", "&0"));

    public CreatureSpawnListener(Main plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent e) {
        Location loc = e.getLocation();
        int dist = (int) Math.floor((Math.sqrt(Math.pow(loc.getX(), 2) + Math.pow(loc.getZ(), 2)))/500);
        if(e.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.CUSTOM)) return; //no loops lol
        switch (e.getEntityType()) {
            case ZOMBIE:
                LivingEntity zombie = (LivingEntity) e.getEntity();
                float health = (dist + 1) * 80.0f;
                Entities.newUndead(zombie, health, health/5, (dist + 1) * 10, "&5Undead " + colors2.get(Math.min(dist, 5)) + "lvl " + Integer.toString((dist + 1) * 10));
                Entities.setArmor(colors.get(Math.min(dist,  5)), zombie);
                break;
            case SKELETON:
                LivingEntity skeleton = (LivingEntity) e.getEntity();
                Entities.newUndeadArcher(skeleton, (dist + 1) * 100.0f, 1000, (dist + 1) * 10, "&fArcher " + colors2.get(Math.min(dist, 5)) + "lvl " + Integer.toString((dist + 1) * 10));
                break;
            case CREEPER:
                LivingEntity creeper = (LivingEntity) e.getEntity();
                Entities.newBomber(creeper, (dist + 1) * 40.0f, 20.0d, (dist + 1) * 10, "&aBomber " + colors2.get(Math.min(dist, 5)) + "lvl " + Integer.toString((dist + 1) * 10));
                break;
            case SPIDER:
                LivingEntity spider = (LivingEntity) e.getEntity();
                Entities.newArachnid(spider, (dist + 1) * 60.0f, 190, (dist + 1) * 10, "&dArachnid " + colors2.get(Math.min(dist, 5)) + "lvl " + Integer.toString((dist + 1) * 10));
                break;
            default:
                e.setCancelled(true);
        }
    }

}
