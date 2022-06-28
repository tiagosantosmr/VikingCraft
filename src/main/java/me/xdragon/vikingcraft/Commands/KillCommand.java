package me.xdragon.vikingcraft.Commands;

import me.xdragon.vikingcraft.Main;
import me.xdragon.vikingcraft.Utils.Utils;
import me.xdragon.vikingutils.VikingUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;

public class KillCommand implements CommandExecutor {

    public Main plugin;

    final String tag = "[&c&lViking&f&lCommands&f]&c- ";

    public KillCommand(Main plugin) {
        this.plugin = plugin;

        plugin.getCommand("kill").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String args[]) {
        System.out.println("boa tarde");
        if(sender.hasPermission("vikingcraft.admin") || !(sender instanceof Player)) {
            System.out.println("boa tarde1");
            if(args.length == 0) {
                sender.sendMessage(VikingUtils.chat(tag + "/kill <all, custom, living, self>"));
            }else if(args.length == 1) {
                if(args[0].equalsIgnoreCase("custom")) {
                    for(Entity entity: Bukkit.getWorld("world").getEntities()) {
                        if(entity.getCustomName() != null && entity instanceof Monster) entity.remove();
                    }
                    return true;

                }else if(args[0].equalsIgnoreCase("living")) {
                    for(Entity entity:Bukkit.getWorld("world").getEntities()) {
                        if(entity != sender && entity instanceof LivingEntity) {
                            entity.remove();
                        }
                    }
                    return true;

                }else if(args[0].equalsIgnoreCase("all")) {
                    for(Entity entity:Bukkit.getWorld("world").getEntities()) {
                        if(!(entity instanceof Player)) entity.remove();
                    }
                    return true;
                }else if(args[0].equalsIgnoreCase("self")) {
                    if(!(sender instanceof Player)) {
                        sender.sendMessage(VikingUtils.chat(tag + "Sender not a valid player."));
                        return false;
                    }
                    Player player = (Player)sender;
                    player.setHealth(0.0d);
                    return true;
                }
            }
            return false;
        }
        return false;
    }

}
