package me.xdragon.vikingcraft.Commands;

import me.xdragon.vikingcraft.Main;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class ContainerCommand implements CommandExecutor {

    public Main plugin;

    public ContainerCommand(Main plugin) {
        this.plugin = plugin;

        plugin.getCommand("container").setExecutor(this);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player && sender.hasPermission("vikingcraft.admin")) {

            if(args.length == 1) {
                Player player = (Player) sender;
                ItemStack item = player.getInventory().getItemInMainHand();
                NamespacedKey key = new NamespacedKey(plugin, args[0]);
                ItemMeta itemMeta = item.getItemMeta();
                sender.sendMessage(String.valueOf(itemMeta.getPersistentDataContainer().get(key, PersistentDataType.DOUBLE)));
                return true;
            }

            Player player = (Player) sender;
            ItemStack item = player.getInventory().getItemInMainHand();
            NamespacedKey key = new NamespacedKey(plugin, args[0]);
            ItemMeta itemMeta = item.getItemMeta();
            itemMeta.getPersistentDataContainer().set(key, PersistentDataType.DOUBLE, Double.valueOf(args[1]));
            item.setItemMeta(itemMeta);
            player.sendMessage(String.valueOf(item.getItemMeta().getPersistentDataContainer().get(key, PersistentDataType.DOUBLE)));
            return true;

        }else {
            return false;
        }
    }

}
