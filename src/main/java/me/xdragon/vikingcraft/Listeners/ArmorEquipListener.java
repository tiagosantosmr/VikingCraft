package me.xdragon.vikingcraft.Listeners;

import com.codingforcookies.armorequip.ArmorEquipEvent;
import me.xdragon.vikingcraft.Main;
import me.xdragon.vikingcraft.Utils.Utils;
import me.xdragon.vikingcraft.Utils.statNames;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.EnumMap;

public class ArmorEquipListener implements Listener {

    public ArmorEquipListener(Main plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onArmorEquip(ArmorEquipEvent e) {

        ItemStack oldPiece = e.getOldArmorPiece();
        ItemStack newPiece = e.getNewArmorPiece();

        if(oldPiece == null || oldPiece.getType() == Material.AIR) {//se a antiga era vazia
            if(newPiece == null || newPiece.getType() == Material.AIR) {//se a nova era vazia
                return;
            }else { //se equipa armadura
                Double armor = 0.0d, magicres = 0.0d, health = 0.0d;
                NamespacedKey armorkey = new NamespacedKey(Main.getPlugin(Main.class), "armor");
                if(newPiece.hasItemMeta() && newPiece.getItemMeta().getPersistentDataContainer() != null && newPiece.getItemMeta().getPersistentDataContainer().has(armorkey, PersistentDataType.DOUBLE)) { //se e armadura valida
                    NamespacedKey magicreskey = new NamespacedKey(Main.getPlugin(Main.class), "magicres");
                    NamespacedKey healthkey = new NamespacedKey(Main.getPlugin(Main.class), "health");
                    PersistentDataContainer container = newPiece.getItemMeta().getPersistentDataContainer();
                    armor = container.get(armorkey, PersistentDataType.DOUBLE);
                    magicres = container.get(magicreskey, PersistentDataType.DOUBLE);
                    health = container.get(healthkey, PersistentDataType.DOUBLE);
                }else {
                    return;
                }
                EnumMap<statNames, Double> stats = new EnumMap<>(statNames.class);
                stats = Main.playerStats.getStats(e.getPlayer().getUniqueId());
                stats.put(statNames.ARMOR, stats.get(statNames.ARMOR) + armor);
                stats.put(statNames.MAGICRES, stats.get(statNames.MAGICRES) + magicres);
                stats.put(statNames.MAXHEALTH, stats.get(statNames.MAXHEALTH) + health);
                Main.playerStats.setStats(e.getPlayer().getUniqueId(), stats);
                Utils.updateScoreboard(e.getPlayer());
            }

        }else {//se a antiga nao era vazia
            Double oldarmor = 0.0d, oldmagicres = 0.0d, oldhealth = 0.0d, newarmor = 0.0d, newmagicres = 0.0d, newhealth = 0.0d;
            NamespacedKey armorkey = new NamespacedKey(Main.getPlugin(Main.class), "armor");
            if(oldPiece.hasItemMeta() && oldPiece.getItemMeta().getPersistentDataContainer().has(armorkey, PersistentDataType.DOUBLE)) { //se a velha armadura e valida
                NamespacedKey magicreskey = new NamespacedKey(Main.getPlugin(Main.class), "magicres");
                NamespacedKey healthkey = new NamespacedKey(Main.getPlugin(Main.class), "health");
                PersistentDataContainer container = oldPiece.getItemMeta().getPersistentDataContainer();
                oldarmor = container.get(armorkey, PersistentDataType.DOUBLE);
                oldmagicres = container.get(magicreskey, PersistentDataType.DOUBLE);
                oldhealth = container.get(healthkey, PersistentDataType.DOUBLE);
            }
            if(newPiece != null && newPiece.getType() != Material.AIR) {
                if(newPiece.hasItemMeta() && newPiece.getItemMeta().getPersistentDataContainer() != null && newPiece.getItemMeta().getPersistentDataContainer().has(armorkey, PersistentDataType.DOUBLE)) {
                    NamespacedKey magicreskey = new NamespacedKey(Main.getPlugin(Main.class), "magicres");
                    NamespacedKey healthkey = new NamespacedKey(Main.getPlugin(Main.class), "health");
                    PersistentDataContainer container = oldPiece.getItemMeta().getPersistentDataContainer();
                    newarmor = container.get(armorkey, PersistentDataType.DOUBLE);
                    newmagicres = container.get(magicreskey, PersistentDataType.DOUBLE);
                    newhealth = container.get(healthkey, PersistentDataType.DOUBLE);
                }
            }
            EnumMap<statNames, Double> stats = new EnumMap<>(statNames.class);
            stats = Main.playerStats.getStats(e.getPlayer().getUniqueId());
            stats.put(statNames.ARMOR, stats.get(statNames.ARMOR) + newarmor - oldarmor);
            stats.put(statNames.MAGICRES, stats.get(statNames.MAGICRES) + newmagicres - oldmagicres);
            stats.put(statNames.MAXHEALTH, stats.get(statNames.MAXHEALTH) + newhealth - oldhealth);
            if(stats.get(statNames.HEALTH) > stats.get(statNames.MAXHEALTH)) {
                stats.put(statNames.HEALTH, stats.get(statNames.MAXHEALTH));
                e.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(Utils.Chat("&c" + String.valueOf(Math.ceil(stats.get(statNames.MAXHEALTH))) + " ❤")));
            }
            Main.playerStats.setStats(e.getPlayer().getUniqueId(), stats);
            Utils.updateScoreboard(e.getPlayer());
        }

    }

}