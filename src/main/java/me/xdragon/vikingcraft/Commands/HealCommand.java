package me.xdragon.vikingcraft.Commands;

import me.xdragon.vikingcraft.Main;
import me.xdragon.vikingcraft.Utils.statNames;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HealCommand implements CommandExecutor {

    Main plugin;

    public HealCommand(Main plugin) {
        this.plugin = plugin;

        plugin.getCommand("heal").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String args[]) {

        if(sender instanceof Player) {
            Player player = (Player) sender;

            if(args.length == 0) {
                Main.playerStats.setStat(player.getUniqueId(), statNames.HEALTH, Main.playerStats.getStat(player.getUniqueId(), statNames.MAXHEALTH));
                player.sendMessage(String.valueOf(Main.playerStats.getStat(player.getUniqueId(), statNames.XP)));
                return true;
            }else if(args.length == 1) {
                Player joao = Bukkit.getPlayer(args[0]);
                Main.playerStats.setStat(joao.getUniqueId(), statNames.HEALTH, Main.playerStats.getStat(joao.getUniqueId(), statNames.MAXHEALTH));
                return true;
            }
            return false;
        }
        return false;

    }

}
