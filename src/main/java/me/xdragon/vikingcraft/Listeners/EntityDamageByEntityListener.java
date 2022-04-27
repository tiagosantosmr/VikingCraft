package me.xdragon.vikingcraft.Listeners;

import me.xdragon.vikingcraft.Main;
import me.xdragon.vikingcraft.Utils.Utils;
import me.xdragon.vikingcraft.Utils.statNames;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;

import java.util.EnumMap;

public class EntityDamageByEntityListener implements Listener {

    public me.xDraGon.CurrencyModule.Main currencymanager;
    String tag = "&a&l[Bank]&a: ";

    public EntityDamageByEntityListener(Main plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    public Main plugin = Main.getPlugin(Main.class);

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {

        if(e.getEntity() instanceof Player) { //se quem recebe dano e player
            e.setDamage(0.0d);
            Player victim = (Player) e.getEntity();
            EnumMap<statNames, Double> victimStats = Main.playerStats.getStats(victim.getUniqueId());
            double armor = victimStats.get(statNames.ARMOR);
            armor = 100 / (100 + armor);
            double health = victimStats.get(statNames.HEALTH);
            double damage = 0;

            if(e.getDamager() instanceof Player) { //se o atacante e um player (player vs player)
                Double critdmg = 0.0d, critrate = 0.0d, attackdmg = 0.0d;
                Player opp = (Player) e.getDamager();
                EnumMap<statNames, Double> attackerStats = Main.playerStats.getStats(opp.getUniqueId());
                ItemStack attackerItem = opp.getInventory().getItemInMainHand();
                if(attackerItem != null && attackerItem.hasItemMeta() && attackerItem.getItemMeta().getPersistentDataContainer() != null) {//se o item de ataque e valido
                    PersistentDataContainer container = attackerItem.getItemMeta().getPersistentDataContainer();
                    NamespacedKey keyattack = new NamespacedKey(plugin, "attackdmg");
                    if(container.has(keyattack, PersistentDataType.DOUBLE)) { //vamos buscar as tags
                        NamespacedKey keycritdmg = new NamespacedKey(plugin, "critdmg");
                        NamespacedKey keycritrate = new NamespacedKey(plugin, "critchance");
                        critdmg = container.get(keycritdmg, PersistentDataType.DOUBLE);
                        critrate = container.get(keycritrate, PersistentDataType.DOUBLE);
                        attackdmg = container.get(keyattack, PersistentDataType.DOUBLE);
                    }
                }
                if(Utils.isCrit(attackerStats.get(statNames.CRITCHANCE) + critrate)) { //calculates basic attack damage
                    damage = (attackerStats.get(statNames.ATTACKDMG) + attackdmg) * (attackerStats.get(statNames.CRITDMG) + critdmg) * 100 / (100 + victimStats.get(statNames.ARMOR));
                }else {
                    damage = (attackerStats.get(statNames.ATTACKDMG) + attackdmg) * 100 / (100 + victimStats.get(statNames.ARMOR));
                }
                damage *= armor;
                if(health - damage <= 0) { //se a vitima morre
                    CurrencyManager currency = new CurrencyManager(currencymanager);
                    currency.removeCurrency(victim, 1000);
                    currency.addCurrency(opp, 1000);
                    victim.sendMessage(Utils.Chat("&cYou have been killed by") + opp.getDisplayName() + "\n" + tag + "&4Hospital fees- 1000$");
                    opp.sendMessage(tag + "&cYou have been awarded 1000$ for killing &b" + victim.getDisplayName());
                    final Vector vec = new Vector(0, 0, 0);
                    victim.setVelocity(vec);
                    victim.setFoodLevel(20);
                    victim.setFireTicks(0);
                    victim.setHealth(20.0d);
                    Location loc = new Location(victim.getWorld(), -11.463d, 67.000d, -5.490d);
                    victim.teleport(loc);
                    victimStats.put(statNames.HEALTH, victimStats.get(statNames.MAXHEALTH));
                    Main.playerStats.setStats(victim.getUniqueId(), victimStats);
                    victim.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(Utils.Chat("&c" + String.valueOf(Math.ceil(victimStats.get(statNames.MAXHEALTH))) + " ❤")));
                    return;
                }else {
                    victimStats.put(statNames.HEALTH, health - damage);
                    Main.playerStats.setStats(victim.getUniqueId(), victimStats);
                    victim.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(Utils.Chat("&c" + String.valueOf(Math.ceil(health - damage)) + " ❤")));
                    return;
                }

            }else if(e.getDamager() instanceof Zombie) { //se o atacante e zombie
                LivingEntity undead = (LivingEntity) e.getDamager();
                NamespacedKey damagekey = new NamespacedKey(plugin, "damage");
                damage = undead.getPersistentDataContainer().get(damagekey, PersistentDataType.DOUBLE);
            }else if(e.getDamager() instanceof Arrow) {
                Arrow arrow = (Arrow) e.getDamager();
                if(arrow.getShooter() instanceof Skeleton) { //this if gets checked
                    LivingEntity skeleton = (LivingEntity) arrow.getShooter();
                    NamespacedKey damagekey = new NamespacedKey(plugin, "damage");
                    damage = skeleton.getPersistentDataContainer().get(damagekey, PersistentDataType.DOUBLE);
                }
            }

            //}else if(e.getDamager() instanceof Bomber) {
            //Bomber attacker = (Bomber) e.getDamager();
            //damage = attacker.attack() * 100 / (victimStats.get(statNames.ARMOR) + 100);
            //}
            if(e.getCause().equals(DamageCause.ENTITY_SWEEP_ATTACK)) {
                damage *= 0.5;
            }
            damage *= armor;
            if(health - damage <= 0) { //se a vitima morre
                CurrencyManager currency = new CurrencyManager(currencymanager);
                currency.removeCurrency(victim, 1000);
                victim.sendMessage(Utils.Chat(tag + "&4Hospital fees- 1000$"));
                final Vector vec = new Vector(0, 0, 0);
                victim.setVelocity(vec);
                victim.setFoodLevel(20);
                victim.setFireTicks(0);
                victim.setHealth(20.0d);
                Location loc = new Location(victim.getWorld(), -11.463d, 67.000d, -5.490d);
                victim.teleport(loc);
                victimStats.put(statNames.HEALTH, victimStats.get(statNames.MAXHEALTH));
                Main.playerStats.setStats(victim.getUniqueId(), victimStats);
                return;
            }else {
                victimStats.put(statNames.HEALTH, health - damage);
                Main.playerStats.setStats(victim.getUniqueId(), victimStats);
                return;
            }
        }else {//se quem recebe dano nao e player
            e.setDamage(0.0d);
            Double damage = 0.0d;
            int ammount = 0;
            Player attacker = null;
            if(e.getDamager() instanceof Player) {
                attacker = (Player) e.getDamager();
                EnumMap<statNames, Double> attackerStats = Main.playerStats.getStats(attacker.getUniqueId());
                damage = attackerStats.get(statNames.ATTACKDMG);
                Double critdmg = attackerStats.get(statNames.CRITDMG), critrate = attackerStats.get(statNames.CRITCHANCE);
                ItemStack weapon = attacker.getInventory().getItemInMainHand();
                if(weapon != null && weapon.hasItemMeta() && weapon.getItemMeta().getPersistentDataContainer() != null) {//se for ataque valido
                    PersistentDataContainer container = weapon.getItemMeta().getPersistentDataContainer();
                    NamespacedKey keyattack = new NamespacedKey(plugin, "attackdmg");
                    if(container.has(keyattack, PersistentDataType.DOUBLE)) { //vamos buscar as tags
                        NamespacedKey keycritdmg = new NamespacedKey(plugin, "critdmg");
                        NamespacedKey keycritrate = new NamespacedKey(plugin, "critchance");
                        critdmg += container.get(keycritdmg, PersistentDataType.DOUBLE);
                        critrate += container.get(keycritrate, PersistentDataType.DOUBLE);
                        damage += container.get(keyattack, PersistentDataType.DOUBLE);
                    }
                }
                if(Utils.isCrit(critrate)) { //calculates basic attack damage
                    damage = damage * critdmg;
                }
            }else if(e.getDamager() instanceof Zombie) { //se o atacante e zombie
                LivingEntity undead = (LivingEntity) e.getDamager();
                NamespacedKey damagekey = new NamespacedKey(plugin, "damage");
                damage = undead.getPersistentDataContainer().get(damagekey, PersistentDataType.DOUBLE);
                ammount = 100;
            }else if(e.getDamager() instanceof Arrow) {
                Arrow arrow = (Arrow) e.getDamager();
                if(arrow.getShooter() instanceof Skeleton) { //this if gets checked
                    LivingEntity skeleton = (LivingEntity) arrow.getShooter();
                    NamespacedKey damagekey = new NamespacedKey(plugin, "damage");
                    damage = skeleton.getPersistentDataContainer().get(damagekey, PersistentDataType.DOUBLE);
                    ammount = 200;
                }
            }
            if(e.getCause().equals(DamageCause.ENTITY_SWEEP_ATTACK)) {
                damage *= 0.5;
            }
            LivingEntity entity = (LivingEntity) e.getEntity();
            PersistentDataContainer container = entity.getPersistentDataContainer();
            NamespacedKey healthkey = new NamespacedKey(plugin, "health");
            NamespacedKey namekey = new NamespacedKey(plugin, "name");
            String name = container.get(namekey, PersistentDataType.STRING);
            Double healthvalue = container.get(healthkey, PersistentDataType.DOUBLE);
            if(damage >= healthvalue) {
                if(attacker != null) {
                    if(e.getEntity().getPersistentDataContainer() != null) {
                        NamespacedKey levelkey = new NamespacedKey(plugin, "level");
                        int level = e.getEntity().getPersistentDataContainer().get(levelkey, PersistentDataType.INTEGER);
                        switch(e.getEntityType()) {
                            case ZOMBIE:
                                Main.playerStats.addStat(attacker.getUniqueId(), statNames.XP, level * 3);
                                break;
                            case SKELETON:
                                Main.playerStats.addStat(attacker.getUniqueId(), statNames.XP, level * 4);
                                break;
                            case CREEPER:
                                Main.playerStats.addStat(attacker.getUniqueId(), statNames.XP, level * 5);
                                break;
                            case SPIDER:
                                Main.playerStats.addStat(attacker.getUniqueId(), statNames.XP, level * 3);
                                break;
                            default:
                                break;
                        }

                        attacker.sendMessage(String.valueOf(Main.playerStats.getStat(attacker.getUniqueId(), statNames.XP)));
                        attacker.setLevel((int)Math.round(Math.log(Main.playerStats.getStat(attacker.getUniqueId(), statNames.XP))));
                    }
                    CurrencyManager currency = new CurrencyManager(currencymanager);
                    currency.addCurrency(attacker, ammount);
                }
                entity.setHealth(0.0d);
                return;
            }
            container.set(healthkey, PersistentDataType.DOUBLE, healthvalue - damage);
            entity.setCustomName(Utils.Chat(name + " &4" + Math.ceil(healthvalue - damage)));
            return;
        }
    }
}
