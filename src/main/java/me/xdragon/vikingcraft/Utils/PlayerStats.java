package me.xdragon.vikingcraft.Utils;

import java.io.*;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerStats {

    private static Map<UUID, EnumMap<statNames, Double>> stats = new HashMap<>();

    private static EnumMap<statNames, Double> defaultStats;

    static {
        defaultStats = new EnumMap<>(statNames.class);
        defaultStats.put(statNames.HEALTH, 100d);
        defaultStats.put(statNames.ATTACKDMG, 10d);
        defaultStats.put(statNames.MAGICDMG, 0d);
        defaultStats.put(statNames.CRITCHANCE, 0.05d);
        defaultStats.put(statNames.CRITDMG,1.5d);
        defaultStats.put(statNames.ARMOR, 30d);
        defaultStats.put(statNames.MAGICRES, 10d);
        defaultStats.put(statNames.XP, 0d);
        defaultStats.put(statNames.MAXHEALTH, 100d);
        defaultStats.put(statNames.HEALTHREGEN, 5.0d);
    }

    private static Map<String, statNames> aux;

    static {
        aux = new HashMap<>();
        aux.put("health", statNames.HEALTH);
        aux.put("attackdmg", statNames.ATTACKDMG);
        aux.put("magicdmg", statNames.MAGICDMG);
        aux.put("critchance", statNames.CRITCHANCE);
        aux.put("critdmg", statNames.CRITDMG);
        aux.put("armor", statNames.ARMOR);
        aux.put("magicres", statNames.MAGICRES);
        aux.put("xp", statNames.XP);
        aux.put("maxhealth", statNames.MAXHEALTH);
        aux.put("healthregen", statNames.HEALTHREGEN);
    }

    public void setStats(UUID uuid, EnumMap<statNames, Double> values) {//100 a funcionar
        stats.put(uuid, values);
    }

    public void setStat(UUID uuid, statNames stat, Double value) {//100 a funcionar
        EnumMap<statNames, Double> aux = stats.get(uuid);
        aux.put(stat, value);
        stats.put(uuid, aux);
    }

    public EnumMap<statNames, Double> getStats(UUID uuid){//100 a funcionar
        return stats.get(uuid);
    }

    public void addStat(UUID uuid, statNames stat, double value) {//100 a funcionar
        double ival = stats.get(uuid).get(stat);
        EnumMap<statNames, Double> istats = stats.get(uuid);
        istats.replace(stat, ival + value);
        stats.replace(uuid, istats);
    }

    public double getStat(UUID uuid, statNames stat) {//100 a funcionar
        return stats.get(uuid).get(stat);
    }

    public void loadFile(File f) throws IOException {
        String split[];
        EnumMap<statNames, Double> stat = new EnumMap<>(statNames.class);
        try(BufferedReader br = new BufferedReader(new FileReader(f))) {
            for(String line; (line = br.readLine()) != null; ) {
                split = line.split("\\s+");
                for(int i = 1; i < 20; i += 2) {
                    stat.put(aux.get(split[i]), Double.parseDouble(split[i + 1])); //vai buscar todos os stats do player
                }
                stats.put(UUID.fromString(split[0]), stat);
            }
        }
    }

    public void addPlayer(UUID uuid) {
        stats.put(uuid, defaultStats);
    }

    public double xpToLvl(double xp) {
        return Math.floor((50 + Math.sqrt(8100 + 280 * xp)) / 140);
    }

    public void saveFile(File f) {
        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter(f);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            new FileWriter(f, false).close();
            for (Map.Entry<UUID, EnumMap<statNames, Double>> entry : stats.entrySet()) {
                UUID uuid = entry.getKey();
                EnumMap<statNames, Double> pstats = entry.getValue();
                System.out.println(pstats);
                printWriter.print(uuid.toString() + " " + Utils.statsToString(pstats).toLowerCase());
            }
            printWriter.close();
            fileWriter.close();

        } catch (IOException e) {
            System.out.println("VikingCraft- Error saving player stats to file");
            e.printStackTrace();
        }

    }
}