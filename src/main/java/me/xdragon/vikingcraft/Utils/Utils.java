package me.xdragon.vikingcraft.Utils;

import me.xdragon.vikingcraft.Main;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Utils {

    public static String Chat(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

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

    private final static TreeMap<Integer, String> map = new TreeMap<Integer, String>();

    static {

        map.put(1000, "M");
        map.put(900, "CM");
        map.put(500, "D");
        map.put(400, "CD");
        map.put(100, "C");
        map.put(90, "XC");
        map.put(50, "L");
        map.put(40, "XL");
        map.put(10, "X");
        map.put(9, "IX");
        map.put(5, "V");
        map.put(4, "IV");
        map.put(1, "I");

    }

    public final static String toRoman(int number) {
        int l = map.floorKey(number);
        if ( number == l ) {
            return map.get(number);
        }
        return map.get(l) + toRoman(number-l);
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

        Score s12 = objective.getScore(Utils.Chat("&6Armor- ") + String.valueOf(stats.get(statNames.ARMOR)));
        Score s11 = objective.getScore(Utils.Chat("&dMagic Res- ") + String.valueOf(stats.get(statNames.MAGICRES)));
        s12.setScore(12);
        s11.setScore(11);

        p.setScoreboard(scoreboard);
    }
}

