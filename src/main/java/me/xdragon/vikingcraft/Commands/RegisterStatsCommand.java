package me.xdragon.vikingcraft.Commands;

import me.xdragon.vikingcraft.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class RegisterStatsCommand implements CommandExecutor {

    public RegisterStatsCommand(Main plugin){
        plugin.getCommand("registerstats").setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Main.playerStats.addPlayer(((Player)sender).getUniqueId());
        return true;
    }
}
