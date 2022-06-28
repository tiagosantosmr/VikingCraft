package me.xdragon.vikingcraft.Commands;

import me.xdragon.vikingcraft.Main;
import me.xdragon.vikingcraft.Utils.Utils;
import me.xdragon.vikingutils.VikingUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemCommand implements CommandExecutor {

    private static final Map<String, Material> swords = new HashMap<String, Material>();
    private static final Map<String, Material> helmets = new HashMap<String, Material>();
    private static final Map<String, Material> chestplates = new HashMap<String, Material>();
    private static final Map<String, Material> legging = new HashMap<String, Material>();
    private static final Map<String, Material> boot = new HashMap<String, Material>();

    static {
        swords.put("wood", Material.WOODEN_SWORD);
        swords.put("iron", Material.IRON_SWORD);
        swords.put("gold", Material.GOLDEN_SWORD);
        swords.put("diamond", Material.DIAMOND_SWORD);
        swords.put("stone", Material.STONE_SWORD);
    }
    static {
        helmets.put("leather", Material.LEATHER_HELMET);
        helmets.put("iron", Material.IRON_HELMET);
        helmets.put("gold", Material.GOLDEN_HELMET);
        helmets.put("diamond", Material.DIAMOND_HELMET);
    }
    static {
        chestplates.put("leather", Material.LEATHER_CHESTPLATE);
        chestplates.put("iron", Material.IRON_CHESTPLATE);
        chestplates.put("gold", Material.GOLDEN_CHESTPLATE);
        chestplates.put("diamond", Material.DIAMOND_CHESTPLATE);
    }
    static {
        legging.put("leather", Material.LEATHER_LEGGINGS);
        legging.put("iron", Material.IRON_LEGGINGS);
        legging.put("gold", Material.GOLDEN_LEGGINGS);
        legging.put("diamond", Material.DIAMOND_LEGGINGS);
    }
    static {
        boot.put("leather", Material.LEATHER_BOOTS);
        boot.put("iron", Material.IRON_BOOTS);
        boot.put("gold", Material.GOLDEN_BOOTS);
        boot.put("diamond", Material.DIAMOND_BOOTS);
    }

    public Main plugin;

    public ItemCommand(Main plugin) {
        this.plugin = plugin;

        plugin.getCommand("item").setExecutor(this);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String args[]) {
        if(sender instanceof Player && sender.hasPermission("vikingcraft.admin")) {
            Player player = (Player) sender;

            if(args.length < 4) {
                sender.sendMessage("/item <sword/helmet/chestplate/leggings/boots> <material> <health/attackdmg> <armor/critchance> <magicres/critdmg> <name>");
                return true;
            }

            if(args[0].equalsIgnoreCase("sword")) {
                ItemStack sword = new ItemStack(swords.get(args[1].toLowerCase()));

                ItemMeta meta = sword.getItemMeta();
                meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                List<Component> lore = new ArrayList<Component>();
                lore.add(Component.text(VikingUtils.chat("&b&lViking&6&lCraft")));
                lore.add(Component.text(VikingUtils.chat("&cAttack Damage: " + args[2])));
                lore.add(Component.text(VikingUtils.chat("&4Critical Damage: " + args[3] + "%")));
                lore.add(Component.text(VikingUtils.chat("&4Critical Chance: " + args[4] + "%")));
                NamespacedKey attackDamage = new NamespacedKey(plugin, "attackdmg");
                NamespacedKey critdmg = new NamespacedKey(plugin, "critdmg");
                NamespacedKey critchance = new NamespacedKey(plugin, "critchance");
                meta.lore(lore);
                PersistentDataContainer container = meta.getPersistentDataContainer();
                container.set(attackDamage, PersistentDataType.DOUBLE, Double.valueOf(args[2]));
                container.set(critdmg, PersistentDataType.DOUBLE, Double.valueOf(args[3]) / 100);
                container.set(critchance, PersistentDataType.DOUBLE, Double.valueOf(args[4]) / 100);
                if(args.length >= 6) {
                    String name = args[5];
                    for(int i = 6; i < args.length; i++){
                        name += " " + args[i];
                    }
                    meta.displayName(Component.text(VikingUtils.chat(name)));
                }
                sword.setItemMeta(meta);
                player.getInventory().addItem(sword);
                return true;
            }else if(args[0].equals("helmet")) {
                ItemStack helmet = new ItemStack(helmets.get(args[1].toLowerCase()));
                ItemMeta meta = helmet.getItemMeta();
                meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                List<Component> lore = new ArrayList<Component>();
                lore.add(Component.text(VikingUtils.chat("&b&lViking&6&lCraft")));
                lore.add(Component.text(VikingUtils.chat("&aHealth: +" + args[2])));
                lore.add(Component.text(VikingUtils.chat("&6Armor: " + args[3])));
                lore.add(Component.text(VikingUtils.chat("&5Magic Res: " + args[4])));
                NamespacedKey maxHealth = new NamespacedKey(plugin, "health");
                NamespacedKey armor = new NamespacedKey(plugin, "armor");
                NamespacedKey magicRes = new NamespacedKey(plugin, "magicres");
                meta.lore(lore);
                PersistentDataContainer container = meta.getPersistentDataContainer();
                container.set(maxHealth, PersistentDataType.DOUBLE, Double.valueOf(args[2]));
                container.set(armor, PersistentDataType.DOUBLE, Double.valueOf(args[3]));
                container.set(magicRes, PersistentDataType.DOUBLE, Double.valueOf(args[4]));
                if(args.length >= 6) {
                    String name = args[5];
                    for(int i = 6; i < args.length; i++){
                        name += " " + args[i];
                    }
                    meta.displayName(Component.text(VikingUtils.chat(name)));
                }
                helmet.setItemMeta(meta);
                player.getInventory().addItem(helmet);
                return true;
            }else if(args[0].equals("chestplate")) {
                ItemStack chestplate = new ItemStack(chestplates.get(args[1].toLowerCase()));
                ItemMeta meta = chestplate.getItemMeta();
                meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                List<Component> lore = new ArrayList<Component>();
                lore.add(Component.text(VikingUtils.chat("&b&lViking&6&lCraft")));
                lore.add(Component.text(VikingUtils.chat("&aHealth: +" + args[2])));
                lore.add(Component.text(VikingUtils.chat("&6Armor: " + args[3])));
                lore.add(Component.text(VikingUtils.chat("&5Magic Res: " + args[4])));
                NamespacedKey maxHealth = new NamespacedKey(plugin, "health");
                NamespacedKey armor = new NamespacedKey(plugin, "armor");
                NamespacedKey magicRes = new NamespacedKey(plugin, "magicres");
                meta.lore(lore);
                PersistentDataContainer container = meta.getPersistentDataContainer();
                container.set(maxHealth, PersistentDataType.DOUBLE, Double.valueOf(args[2]));
                container.set(armor, PersistentDataType.DOUBLE, Double.valueOf(args[3]));
                container.set(magicRes, PersistentDataType.DOUBLE, Double.valueOf(args[4]));
                if(args.length >= 6) {
                    String name = args[5];
                    for(int i = 6; i < args.length; i++){
                        name += " " + args[i];
                    }
                    meta.displayName(Component.text(VikingUtils.chat(name)));
                }
                chestplate.setItemMeta(meta);
                player.getInventory().addItem(chestplate);
                return true;
            }else if(args[0].equals("leggings")) {
                ItemStack leggings = new ItemStack(legging.get(args[1].toLowerCase()));
                ItemMeta meta = leggings.getItemMeta();
                meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                List<Component> lore = new ArrayList<Component>();
                lore.add(Component.text(VikingUtils.chat("&b&lViking&6&lCraft")));
                lore.add(Component.text(VikingUtils.chat("&aHealth: +" + args[2])));
                lore.add(Component.text(VikingUtils.chat("&6Armor: " + args[3])));
                lore.add(Component.text(VikingUtils.chat("&5Magic Res: " + args[4])));
                NamespacedKey maxHealth = new NamespacedKey(plugin, "health");
                NamespacedKey armor = new NamespacedKey(plugin, "armor");
                NamespacedKey magicRes = new NamespacedKey(plugin, "magicres");
                meta.lore(lore);
                PersistentDataContainer container = meta.getPersistentDataContainer();
                container.set(maxHealth, PersistentDataType.DOUBLE, Double.valueOf(args[2]));
                container.set(armor, PersistentDataType.DOUBLE, Double.valueOf(args[3]));
                container.set(magicRes, PersistentDataType.DOUBLE, Double.valueOf(args[4]));
                if(args.length >= 6) {
                    String name = args[5];
                    for(int i = 6; i < args.length; i++){
                        name += " " + args[i];
                    }
                    meta.displayName(Component.text(VikingUtils.chat(name)));
                }
                leggings.setItemMeta(meta);
                player.getInventory().addItem(leggings);
                return true;
            }else if(args[0].equals("boots")) {
                ItemStack boots = new ItemStack(boot.get(args[1].toLowerCase()));
                ItemMeta meta = boots.getItemMeta();
                meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                List<Component> lore = new ArrayList<Component>();
                lore.add(Component.text(VikingUtils.chat("&b&lViking&6&lCraft")));
                lore.add(Component.text(VikingUtils.chat("&aHealth: +" + args[2])));
                lore.add(Component.text(VikingUtils.chat("&6Armor: " + args[3])));
                lore.add(Component.text(VikingUtils.chat("&5Magic Res: " + args[4])));
                NamespacedKey maxHealth = new NamespacedKey(plugin, "health");
                NamespacedKey armor = new NamespacedKey(plugin, "armor");
                NamespacedKey magicRes = new NamespacedKey(plugin, "magicres");
                meta.lore(lore);
                PersistentDataContainer container = meta.getPersistentDataContainer();
                container.set(maxHealth, PersistentDataType.DOUBLE, Double.valueOf(args[2]));
                container.set(armor, PersistentDataType.DOUBLE, Double.valueOf(args[3]));
                container.set(magicRes, PersistentDataType.DOUBLE, Double.valueOf(args[4]));
                if(args.length >= 6) {
                    String name = args[5];
                    for(int i = 6; i < args.length; i++){
                        name += " " + args[i];
                    }
                    meta.displayName(Component.text(VikingUtils.chat(name)));
                }
                boots.setItemMeta(meta);
                player.getInventory().addItem(boots);
                return true;
            }
            return false;
        }else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou do not have permission to run this command."));
            return false;
        }
    }

}
