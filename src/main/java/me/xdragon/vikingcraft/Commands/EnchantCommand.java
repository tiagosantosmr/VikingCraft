package me.xdragon.vikingcraft.Commands;

import me.xdragon.vikingcraft.Main;
import me.xdragon.vikingcraft.Utils.Utils;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnchantCommand implements CommandExecutor {

    public Main plugin;

    final String tag = "[&c&lViking&f&lCommands&f]&c- ";

    @SuppressWarnings("serial")
    final Map<String, Enchantment> encantamentos = new HashMap<String, Enchantment>(){{
        put("sharpness", Enchantment.DAMAGE_ALL);
        put("fortune", Enchantment.LOOT_BONUS_BLOCKS);
        put("power", Enchantment.ARROW_DAMAGE);
        put("flame", Enchantment.ARROW_FIRE);
        put("infinity", Enchantment.ARROW_INFINITE);
        put("punch", Enchantment.ARROW_KNOCKBACK);
        put("bane", Enchantment.DAMAGE_ARTHROPODS);
        put("smite", Enchantment.DAMAGE_UNDEAD);
        put("unbreaking", Enchantment.DURABILITY);
        put("fire_aspect", Enchantment.FIRE_ASPECT);
        put("knockback", Enchantment.KNOCKBACK);
        put("mending", Enchantment.MENDING);
        put("efficiency", Enchantment.DIG_SPEED);
        put("thorns", Enchantment.THORNS);
        put("feather", Enchantment.PROTECTION_FALL);
        put("protection", Enchantment.PROTECTION_ENVIRONMENTAL);
        put("silk_touch", Enchantment.SILK_TOUCH);
        put("fire_protection", Enchantment.PROTECTION_FIRE);
        put("blast_protection", Enchantment.PROTECTION_EXPLOSIONS);
        put("projectile_protection", Enchantment.PROTECTION_PROJECTILE);
        put("respiration", Enchantment.OXYGEN);
        put("depth_strider", Enchantment.DEPTH_STRIDER);
        put("frost_walker", Enchantment.FROST_WALKER);
        put("binding", Enchantment.BINDING_CURSE);
        put("soul_speed", Enchantment.SOUL_SPEED);
        put("sweeping", Enchantment.SWEEPING_EDGE);
        put("impaling", Enchantment.IMPALING);
        put("riptide", Enchantment.RIPTIDE);
        put("channeling", Enchantment.CHANNELING);
        put("multishot", Enchantment.MULTISHOT);
        put("quick_charge", Enchantment.QUICK_CHARGE);
        put("piercing", Enchantment.PIERCING);
        put("vanishing", Enchantment.VANISHING_CURSE);
    }};

    @SuppressWarnings("serial")
    final Map<String, String> nomes = new HashMap<String, String>(){{
        put("sharpness", "&bSharpness");
        put("fire_aspect", "&cFire Aspect");
        put("power", "&bPower");
        put("flame", "&cFlame");
        put("infinity", "&5Infinity");
        put("punch", "&fPunch");
        put("bane", "&7Bane of Arthropods");
        put("smite", "&7Smite");
        put("unbreaking", "&8Unbreaking");
        put("knockback", "&fKnockback");
        put("efficiency", "&aEfficiency");
        put("mending", "&9Mending");
        put("thorns", "&6Thorns");
        put("feather", "&bFeather Falling");
        put("protection", "&6Protection");
        put("silk_touch", "&bSilk Touch");
        put("fire_protection", "&4Fire Protection");
        put("blast_protection", "&0Blast Protection");
        put("projectile_protection", "&eProjectile Protection");
        put("respiration", "&9Respiration");
        put("depth_strider", "&1Depth Strider");
        put("frost_walker", "&fFrost Walker");
        put("binding", "&5Curse of Binding");
        put("soul_speed", "&5Soul Speed");
        put("sweeping", "&3Sweeping Edge");
        put("impaling", "&7Impaling");
        put("riptide", "&3Riptide");
        put("channeling", "&eChanneling");
        put("multishot", "&1Multishot");
        put("quick_charge", "&9Quick Charge");
        put("piercing", "&7Piercing");
        put("vanishing", "&5Curse of Vanishing");
        put("fortune", "&9Fortune");
    }};

    public EnchantCommand(Main plugin) {
        this.plugin = plugin;

        plugin.getCommand("enchant").setExecutor(this);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        if(sender instanceof Player && sender.hasPermission("enchantperm"))
            if(args.length == 0) {
                sender.sendMessage(Utils.Chat(tag + "&c/enchant <enchantment> <level>"));
                Player player = (Player) sender;
                System.out.println(Main.playerStats.getStats(player.getUniqueId()));
                return false;
            }else if(args.length == 1) {
                sender.sendMessage(Utils.Chat(tag + "&c/enchant <enchantment> <level>"));
            }else if(args.length == 2) {
                if(((Player)sender).getInventory().getItemInMainHand().getType() == Material.AIR) {
                    sender.sendMessage(Utils.Chat(tag + "&cYou must have an item in your hand to be able to use this command"));
                    return false;
                }else { // se tem item na mao
                    try {
                        int valor = Integer.parseInt(args[1]);
                        if (valor < 0) throw new Exception(); // se valor < 0, n e valido
                    }catch(Exception e){
                        e.printStackTrace();
                        sender.sendMessage(Utils.Chat(tag + "&cNao e valido"));
                        return false;
                    }
                    if(encantamentos.get(args[0].toLowerCase()) != null) { //se args[0] e um encantamento valido
                        Player player = (Player) sender;
                        ItemStack item = player.getInventory().getItemInMainHand();
                        if(!item.hasItemMeta() || !item.getItemMeta().lore().contains(Utils.Chat("&b&lViking&6&lCraft"))) {
                            List<Component> lore = new ArrayList<Component>();
                            lore.add(Component.text(Utils.Chat("&cVikingCraft")));
                            item.setItemMeta(null);
                            ItemMeta meta = item.getItemMeta();
                            meta.lore(lore);
                            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                            item.setItemMeta(meta);
                        }
                        int valor = Integer.parseInt(args[1]);
                        List<Component> lore = item.getItemMeta().lore();
                        if(item.containsEnchantment(encantamentos.get(args[0].toLowerCase()))) { //se ja contem o encantamento
                            int index = Utils.findLoreIndexOfContains(lore, args[0]);
                            item.addUnsafeEnchantment(encantamentos.get(args[0].toLowerCase()), valor);
                            ItemMeta meta = item.getItemMeta();
                            if(valor == 0) {
                                lore.remove(index);
                                meta.lore(lore);
                                item.setItemMeta(meta);
                                item.removeEnchantment(encantamentos.get(args[0].toLowerCase()));
                                return true;
                            }
                            lore.set(index, Component.text(Utils.Chat(nomes.get(args[0].toLowerCase()) + " " + Utils.toRoman(valor))));
                            meta.lore(lore);
                            item.setItemMeta(meta);
                            return true;
                        }else { //se nao tem encantamento
                            if(valor == 0) {
                                sender.sendMessage(Utils.Chat(tag + "&cNao contem o encantamento"));
                                return false;
                            }
                            lore.add(Component.text(Utils.Chat(nomes.get(args[0].toLowerCase()) + " " + Utils.toRoman(valor))));
                            item.addUnsafeEnchantment(encantamentos.get(args[0].toLowerCase()), valor);
                            ItemMeta meta = item.getItemMeta();
                            meta.lore(lore);
                            item.setItemMeta(meta);
                            return true;
                        }
                    }else { // se encantamento nao e valido
                        sender.sendMessage(Utils.Chat(tag + "&cNao e valido"));
                        return false;
                    }
                }
            }
        return false;
    }

}
