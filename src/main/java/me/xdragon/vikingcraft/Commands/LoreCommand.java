package me.xdragon.vikingcraft.Commands;

import me.xdragon.vikingcraft.Main;
import me.xdragon.vikingcraft.Utils.Utils;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class LoreCommand implements CommandExecutor {

    public Main plugin;

    public LoreCommand(Main plugin) {
        this.plugin = plugin;

        plugin.getCommand("lore").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String args[]) {
        if(sender instanceof Player && sender.hasPermission("vikingcraft.admin")) {
            Player player = (Player) sender;

            if(player.getInventory().getItemInMainHand().getType() == Material.AIR) {
                sender.sendMessage(Utils.Chat(Main.VCommands + "Nenhum item encontrado"));
                return false;
            }
            if(args.length == 0) { //nao ha argmentos
                sender.sendMessage(Utils.Chat(Main.VCommands + "Usage: /lore <add/set/>"));
                return false;
            }
            if(args[0].equalsIgnoreCase("add")) {
                ItemStack item = player.getInventory().getItemInMainHand();
                ItemMeta meta = item.getItemMeta();
                List<Component> lore;
                if(!item.getItemMeta().hasLore()) { //se nao tem lore
                    lore = new ArrayList<Component>();
                }else {
                    lore = meta.lore();
                }
                if(args.length == 1) { //adicionar espacops
                    lore.add(Component.text(""));
                    meta.lore(lore);
                    item.setItemMeta(meta);
                    return true;
                }
                String res = "";
                for(int i = 1; i < args.length; i++) { // /lore add <1> <2> <3> ...
                    res += args[i] + " ";
                }
                lore.add(Component.text(Utils.Chat(res)));
                meta.lore(lore);
                item.setItemMeta(meta);
                return true;
            }else if(args[0].equalsIgnoreCase("set")) {
                ItemStack item = player.getInventory().getItemInMainHand();
                ItemMeta meta = item.getItemMeta();
                List<Component> lore;
                if(args.length == 1) {
                    lore = new ArrayList<Component>();
                    meta.lore(lore);
                    item.setItemMeta(meta);
                    return true;
                }
                lore = new ArrayList<Component>();
                String res = "";
                for(int i = 1; i < args.length; i++) { // /lore set <1> <2> <3> ...
                    res += args[i] + " ";
                }
                lore.add(Component.text(Utils.Chat(res)));
                meta.lore(lore);
                item.setItemMeta(meta);
                return true;
            }else if(args[0].equalsIgnoreCase("remove")) {
                if(!player.getInventory().getItemInMainHand().getItemMeta().hasLore()) {
                    sender.sendMessage(Utils.Chat(Main.VCommands + "Item ja se encontra sem lore."));
                    return false;
                }
                ItemStack item = player.getInventory().getItemInMainHand();
                ItemMeta meta = item.getItemMeta();
                List<Component> lore = meta.lore();
                if(args.length == 1) {
                    lore = new ArrayList<Component>();
                    meta.lore(lore);
                    item.setItemMeta(meta);
                    return true;
                }else {
                    if(args.length > 2) {
                        sender.sendMessage(Utils.Chat(Main.VCommands + "/lore remove <int>"));
                        return true;
                    }
                    int valor;
                    try {
                        valor = Integer.parseInt(args[1]);
                        if(valor <= 0) throw new Exception();
                    }catch(Exception e){
                        sender.sendMessage(Utils.Chat(Main.VCommands + "Indice invalido"));
                        return false;
                    }
                    if(valor > lore.size()) {
                        sender.sendMessage(Utils.Chat(Main.VCommands + "Index invalido"));
                        return false;
                    }else {
                        lore.remove(valor - 1);
                        meta.lore(lore);
                        item.setItemMeta(meta);
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
