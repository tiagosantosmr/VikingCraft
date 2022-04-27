package me.xdragon.vikingcraft.Listeners;

import me.xdragon.vikingcraft.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class EntityDeathListener implements Listener {
    public Main plugin;
    public me.xDraGon.CurrencyModule.Main currencymanager;

    public EntityDeathListener(Main plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {

        if(e.getEntity().getKiller() instanceof Player) {
            CurrencyManager currency = new CurrencyManager(currencymanager);
            Player p = e.getEntity().getKiller();
            switch(e.getEntityType()) {
                case COW:
                    currency.addCurrency(p, 2);
                    break;
                case ZOMBIE:
                    currency.addCurrency(p, 10);
                    System.out.println("joao");
                    break;
                case SKELETON:
                    currency.addCurrency(p, 10);
                    break;
                case WITHER_SKELETON:
                    currency.addCurrency(p, 1500);
                    break;

                default:
                    break;

            }
        }

    }
}
