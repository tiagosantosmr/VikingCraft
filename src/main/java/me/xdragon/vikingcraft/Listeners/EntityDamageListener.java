package me.xdragon.vikingcraft.Listeners;

import me.xdragon.vikingcraft.Utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.util.Vector;

public class EntityDamageListener implements Listener {

    public Main plugin;
    public me.xDraGon.CurrencyModule.Main currencymanager;
    String tag = "&a&l[Bank]&a: ";

    public EntityDamageListener(Main plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent e) {

        if(e.getEntity() instanceof Player) { //se quem recebe dano e um player
            Player victim = (Player) e.getEntity();
            if(victim.getHealth() - e.getDamage() > 0) {// se n morre

            }
            else { //se morre
                e.setCancelled(true);
                final Vector vec = new Vector(0, 0, 0);
                victim.setVelocity(vec);
                victim.setFoodLevel(20);
                victim.setFireTicks(0);
                victim.setHealth(20.0d);
                Location loc = new Location(victim.getWorld(), -11.463d, 67.000d, -5.490d);
                victim.teleport(loc);
                CurrencyManager currency = new CurrencyManager(currencymanager);
                currency.removeCurrency(victim, 1000);
                victim.sendMessage(Utils.Chat("&4Hospital fees: 1000$"));
                if(victim.getKiller() instanceof Player) {
                    if (victim.getKiller() != victim) {
                        victim.sendMessage(Utils.Chat(tag + "&bYou have been killed by &a" + victim.getKiller().getName()));
                        victim.getKiller().sendMessage(Utils.Chat(tag + "&cYou have killed &b" + victim.getName()));
                        currency.addCurrency(victim.getKiller(), 1000);
                    }else {
                        victim.sendMessage(Utils.Chat("&5Why would you do that?"));
                    }

                }
            }
        }
        Entity en = e.getEntity();
        if(en instanceof Undead) {
            Undead entity = (Undead) en;
            if(entity.health() - e.getDamage() <= 0) {
                entity.killEntity();
            }
        }else if(en instanceof UndeadArcher) {
            UndeadArcher entity = (UndeadArcher) en;
            if(entity.health() - e.getDamage() <= 0) {
                entity.killEntity();
            }
        }else if(en instanceof Bomber) {
            Bomber entity = (Bomber) en;
            if(entity.health() - e.getDamage() <= 0) {
                entity.killEntity();
            }
        }
    }
}
