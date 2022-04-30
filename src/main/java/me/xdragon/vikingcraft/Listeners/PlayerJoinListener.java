package me.xdragon.vikingcraft.Listeners;

import me.xdragon.vikingcraft.Main;
import me.xdragon.vikingcraft.Utils.Utils;
import me.xdragon.vikingcraft.Utils.statNames;
import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class PlayerJoinListener implements Listener {

    public Main plugin;

    public PlayerJoinListener(Main plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        double lyingHealth = 0;
        double lyingArmor = 0;
        double lyingMagicRes = 0;

        NamespacedKey armorkey = new NamespacedKey(Main.getPlugin(Main.class), "armor");
        NamespacedKey magicreskey = new NamespacedKey(Main.getPlugin(Main.class), "magicres");
        NamespacedKey healthkey = new NamespacedKey(Main.getPlugin(Main.class), "health");
        for(ItemStack is: player.getInventory().getArmorContents()){
            if(is != null && is.hasItemMeta() && !is.getItemMeta().getPersistentDataContainer().isEmpty()){
                PersistentDataContainer container = is.getItemMeta().getPersistentDataContainer();
                lyingArmor += container.get(armorkey, PersistentDataType.DOUBLE);
                lyingMagicRes += container.get(magicreskey, PersistentDataType.DOUBLE);
                lyingHealth += container.get(healthkey, PersistentDataType.DOUBLE);
            }
        }
        Main.playerStats.setStat(player.getUniqueId(), statNames.BONUSHEALTH, Main.playerStats.getStat(player.getUniqueId(), statNames.BONUSHEALTH) - lyingHealth);
        Main.playerStats.setStat(player.getUniqueId(), statNames.ARMOR, Main.playerStats.getStat(player.getUniqueId(), statNames.ARMOR) - lyingArmor);
        Main.playerStats.setStat(player.getUniqueId(), statNames.MAGICRES, Main.playerStats.getStat(player.getUniqueId(), statNames.MAGICRES) - lyingMagicRes);


        if(!player.hasPlayedBefore()) {
            Main.playerStats.addPlayer(player.getUniqueId());
        }
        Utils.updateScoreboard(player);
        player.sendActionBar(Component.text(Utils.Chat("&c" + String.valueOf(Main.playerStats.getStat(player.getUniqueId(), statNames.HEALTH)) + " ❤")));
    }

}

