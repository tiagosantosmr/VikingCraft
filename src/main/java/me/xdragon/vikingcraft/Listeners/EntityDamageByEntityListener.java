package me.xdragon.vikingcraft.Listeners;

import me.xdragon.vikingcraft.Main;
import me.xdragon.vikingcraft.Utils.Utils;
import me.xdragon.vikingcraft.Utils.statNames;
import me.xdragon.vikingutils.VikingUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;

import java.util.EnumMap;

public class EntityDamageByEntityListener implements Listener {

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
            double magicres = victimStats.get(statNames.MAGICRES);
            armor = 100 / (100 + armor);
            magicres = 100 / (100 + magicres);
            double health = victimStats.get(statNames.HEALTH);
            double damage = 0;

            if(e.getDamager() instanceof Player) { //se o atacante e um player (player vs player)
                Double critdmg = 0.0d, critrate = 0.0d, attackdmg = 0.0d;
                Player opp = (Player) e.getDamager();
                EnumMap<statNames, Double> attackerStats = Main.playerStats.getStats(opp.getUniqueId());
                ItemStack attackerItem = opp.getInventory().getItemInMainHand();
                if(attackerItem.getType() == Material.AIR && attackerItem.hasItemMeta() && !attackerItem.getItemMeta().getPersistentDataContainer().isEmpty()) {//se o item de ataque e valido
                    PersistentDataContainer container = attackerItem.getItemMeta().getPersistentDataContainer();
                    NamespacedKey keyattack = new NamespacedKey(plugin, "attackdmg");
                    NamespacedKey keymagic = new NamespacedKey(plugin, "magicdmg");
                    if(container.has(keyattack, PersistentDataType.DOUBLE)) { //if physical weapon
                        NamespacedKey keycritdmg = new NamespacedKey(plugin, "critdmg");
                        NamespacedKey keycritrate = new NamespacedKey(plugin, "critchance");
                        critdmg = container.get(keycritdmg, PersistentDataType.DOUBLE);
                        critrate = container.get(keycritrate, PersistentDataType.DOUBLE);
                        attackdmg = container.get(keyattack, PersistentDataType.DOUBLE);
                        if(Utils.isCrit(attackerStats.get(statNames.CRITCHANCE) + critrate)) { //calculates basic attack damage
                            damage = (attackerStats.get(statNames.ATTACKDMG) + attackdmg) * (attackerStats.get(statNames.CRITDMG) + critdmg) * 100 / (100 + victimStats.get(statNames.ARMOR));
                        }else {
                            damage = (attackerStats.get(statNames.ATTACKDMG) + attackdmg) * 100 / (100 + victimStats.get(statNames.ARMOR));
                        }
                        damage *= armor;
                    }else if(container.has(keyattack, PersistentDataType.DOUBLE)){ //if magic weapon
                        damage = container.get(keymagic, PersistentDataType.DOUBLE) * magicres;

                    }
                }else{ //if player weapon is fist or random object (can be changed later to fist-only to promote knuckle builds)
                    attackdmg = Main.BASEFISTDAMAGE;
                    if(Utils.isCrit(attackerStats.get(statNames.CRITCHANCE))) { //calculates basic attack damage
                        damage = (attackerStats.get(statNames.ATTACKDMG) + attackdmg) * (attackerStats.get(statNames.CRITDMG));
                    }else {
                        damage = (attackerStats.get(statNames.ATTACKDMG) + attackdmg);
                    }
                }

                if(health - damage <= 0) { //se player mata player, manda mensagem no chat
                    victim.sendMessage(VikingUtils.chat("&cYou have been killed by") + opp.displayName());
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
            if(e.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK)) {
                damage *= 0.5;
            }
            damage *= armor;
            if(health - damage <= 0) { //se a vitima morre
                final Vector vec = new Vector(0, 0, 0);
                victim.setVelocity(vec);
                victim.setFoodLevel(20);
                victim.setFireTicks(0);
                victim.setHealth(20.0d);
                Location loc = new Location(victim.getWorld(), -11.463d, 67.000d, -5.490d);
                victim.teleport(loc);
                victimStats.put(statNames.HEALTH, victimStats.get(statNames.MAXHEALTH));
                Main.playerStats.setStats(victim.getUniqueId(), victimStats);
            }else {
                victimStats.put(statNames.HEALTH, health - damage);
                Main.playerStats.setStats(victim.getUniqueId(), victimStats);
            }
            victim.sendActionBar(Component.text(VikingUtils.chat("&c" + String.valueOf(Main.playerStats.getStat(victim.getUniqueId(), statNames.HEALTH)) + " ❤")));
        }else {//se quem recebe dano nao e player
            e.setDamage(0.0d);
            Double damage = 0.0d;
            int ammount = 0;
            Player attacker = null;
            if(e.getDamager() instanceof Player) { //se quem ataca e player
                attacker = (Player) e.getDamager();
                EnumMap<statNames, Double> attackerStats = Main.playerStats.getStats(attacker.getUniqueId());
                damage = attackerStats.get(statNames.ATTACKDMG);
                Double critdmg = attackerStats.get(statNames.CRITDMG), critrate = attackerStats.get(statNames.CRITCHANCE);
                ItemStack weapon = attacker.getInventory().getItemInMainHand();
                if(weapon.getType() == Material.AIR && weapon.hasItemMeta() && !weapon.getItemMeta().getPersistentDataContainer().isEmpty()) {//se for ataque valido
                    PersistentDataContainer container = weapon.getItemMeta().getPersistentDataContainer();
                    NamespacedKey keyattack = new NamespacedKey(plugin, "attackdmg");
                    if(container.has(keyattack, PersistentDataType.DOUBLE)) { //vamos buscar as tags
                        NamespacedKey keycritdmg = new NamespacedKey(plugin, "critdmg");
                        NamespacedKey keycritrate = new NamespacedKey(plugin, "critchance");
                        critdmg += container.get(keycritdmg, PersistentDataType.DOUBLE);
                        critrate += container.get(keycritrate, PersistentDataType.DOUBLE);
                        damage += container.get(keyattack, PersistentDataType.DOUBLE);
                    }
                }else{ //if player weapon is fist or random object (can be changed later to fist-only to promote knuckle builds)
                    damage = Main.BASEFISTDAMAGE;
                }
                if(Utils.isCrit(critrate)) { //calculates basic attack damage
                    damage = damage * critdmg;
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
            if(e.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK)) {
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
                    if(!e.getEntity().getPersistentDataContainer().isEmpty()) {
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
                    //CurrencyManager currency = new CurrencyManager();
                    //currency.addCurrency(attacker.getUniqueId(), ammount);
                }
                entity.setHealth(0.0d);
                return;
            }
            container.set(healthkey, PersistentDataType.DOUBLE, healthvalue - damage);
            entity.setCustomName(VikingUtils.chat(name + " &4" + Math.ceil(healthvalue - damage)));
        }
    }
}
