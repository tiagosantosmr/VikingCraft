package me.xdragon.vikingcraft.Utils;

import me.xdragon.vikingcraft.Main;
import me.xdragon.vikingutils.VikingUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class Utils {

    public static boolean containsPartialIgnoreCase(List<String> list, String s) {
        if(list.size() == 0) return false;
        for(String el:list) {
            if(el.toLowerCase().contains(s.toLowerCase())){
                return true;
            }
        }
        return false;
    }

    public static int findLoreIndexOfContains(List<Component> list, String s) {
        if(list.size() == 0) return -1;
        int i = 0;
        for(Component el:list) {
            TextComponent tc = (TextComponent) el;
            if(tc.content().toLowerCase().contains(s.toLowerCase())){
                return i;
            }i++;
        }
        return -1;
    }

    public static int findIndexOfContains(List<String> list, String s) {
        if(list.size() == 0) return -1;
        int i = 0;
        for(String el:list) {
            if(el.toLowerCase().contains(s.toLowerCase())){
                return i;
            }i++;
        }
        return -1;
    }



    public static boolean isCrit(double critChance) {
        return Math.random() <= critChance;
    }

    public final static String statsToString(EnumMap<statNames, Double> stats) {
        String res = "";
        for (Map.Entry<statNames, Double> entry : stats.entrySet()) {
            res += entry.getKey().name() + " ";
            res += String.valueOf(entry.getValue()) + " ";
        }
        return res;
    }

    public static void updateScoreboard(Player p) {

        EnumMap<statNames, Double> stats = Main.playerStats.getStats(p.getUniqueId());

        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard scoreboard = manager.getNewScoreboard();

        Objective objective = scoreboard.registerNewObjective("test", "dummy", Component.text("Player Stats"));
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        Score s12 = objective.getScore(VikingUtils.chat("&6Armor- ") + String.valueOf(stats.get(statNames.ARMOR)));
        Score s11 = objective.getScore(VikingUtils.chat("&dMagic Res- ") + String.valueOf(stats.get(statNames.MAGICRES)));
        s12.setScore(12);
        s11.setScore(11);

        p.setScoreboard(scoreboard);
    }
}

