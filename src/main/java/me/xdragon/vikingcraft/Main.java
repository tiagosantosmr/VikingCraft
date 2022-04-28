package me.xdragon.vikingcraft;

import me.xdragon.vikingcraft.Commands.*;
import me.xdragon.vikingcraft.Listeners.*;
import me.xdragon.vikingcraft.Utils.ActionBarTask;
import me.xdragon.vikingcraft.Utils.PlayerStats;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;
import java.io.IOException;

public final class Main extends JavaPlugin {

    public static PlayerStats playerStats = new PlayerStats();

    public static final String noPermissions = ChatColor.translateAlternateColorCodes('&', "&cYou do not have pers");
    public static final String VCommands = "[&c&lViking&f&lCommands&f]&c- ";

    @Override
    public void onEnable() {
        // Plugin startup logic
        File f = new File(this.getDataFolder().getParentFile().getAbsolutePath() + "\\VikingCraft");
        boolean exists = true;
        if(!f.exists()) { //se nao existe plugins/VikingCraft
            f.mkdir();
        }
        f = new File(this.getDataFolder().getParentFile().getAbsolutePath() + "\\VikingCraft\\playerStats.yml");
        if(!f.exists()) { //se nao existe plugins/VikingCraft/playerStats.yml
            try {
                f.createNewFile();
                System.out.println("VikingCraft- playerStats.yml file created");
            } catch (IOException e) {
                System.out.println("VikingCraft- Error creating playerStats.yml file");
                e.printStackTrace();
            }
        }else {
            try {
                playerStats.loadFile(f);
                System.out.println("VikingCraft- Player Stats successfully loaded");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("VikingCraft- Error loading Player Stats");
            }
        }
        registerCommands();
        registerListeners();

        BukkitTask task = new ActionBarTask(this).runTaskTimer(this, 10, 40);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        File f = new File(this.getDataFolder().getParentFile().getAbsolutePath() + "\\VikingCraft\\playerStats.yml");
        playerStats.saveFile(f);
        System.out.println("VikingCraft- Player stats successfully saved to file");
    }

    public void registerCommands() {
        new KillCommand(this);
        new EnchantCommand(this);
        new LoreCommand(this);
        new ContainerCommand(this);
        new ItemCommand(this);
        new HealCommand(this);
        new RegisterStatsCommand(this);
    }

    public void registerListeners() {
        new EntityDamageByEntityListener(this);
        new EntityDeathListener(this);
        new CreatureSpawnListener(this);
        new PlayerJoinListener(this);
        new ArmorEquipListener(this);
        new ChatListener(this);
    }
}
