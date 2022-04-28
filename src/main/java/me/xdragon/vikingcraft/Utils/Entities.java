package me.xdragon.vikingcraft.Utils;

import me.xdragon.vikingcraft.Main;
import org.bukkit.Color;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class Entities{
    //float health;
    //double damage;
    //public Undead(Location loc, float health, String name) {
    //super(EntityTypes.ZOMBIE, ((CraftWorld) loc.getWorld()).getHandle());
    //this.setPosition(loc.getX(), loc.getY(), loc.getZ());
    //this.health = health;
    //this.damage = health / 25;
    //this.setHealth(health);
    //this.setCustomName(new ChatComponentText(Utils.Chat(name)));
    //this.setCustomNameVisible(true);
    //this.goalSelector.a(2, new PathfinderGoalRandomStrollLand(this, 2.0d));
    //this.goalSelector.a(3, new PathfinderGoalRandomLookaround(this));
    //}

    public static void newUndead(LivingEntity zombie, double health, double damage, int level, String name) {
        Main plugin = Main.getPlugin(Main.class);
        PersistentDataContainer container = zombie.getPersistentDataContainer();
        NamespacedKey healthkey = new NamespacedKey(plugin, "health");
        NamespacedKey damagekey = new NamespacedKey(plugin, "damage");
        NamespacedKey typekey = new NamespacedKey(plugin, "undead");
        NamespacedKey namekey = new NamespacedKey(plugin, "name");
        NamespacedKey levelkey = new NamespacedKey(plugin, "level");
        container.set(namekey, PersistentDataType.STRING, name);
        container.set(healthkey, PersistentDataType.DOUBLE, health);
        container.set(damagekey, PersistentDataType.DOUBLE, damage);
        container.set(typekey, PersistentDataType.STRING, "undead");
        container.set(levelkey, PersistentDataType.INTEGER, level);
        zombie.setCustomName(Utils.Chat(name + " &4" + health));
        zombie.setCustomNameVisible(true);
    }

    public static void newUndeadArcher(LivingEntity skeleton, double health, double damage, int level, String name) {
        Main plugin = Main.getPlugin(Main.class);
        PersistentDataContainer container = skeleton.getPersistentDataContainer();
        NamespacedKey healthkey = new NamespacedKey(plugin, "health");
        NamespacedKey damagekey = new NamespacedKey(plugin, "damage");
        NamespacedKey typekey = new NamespacedKey(plugin, "undead");
        NamespacedKey namekey = new NamespacedKey(plugin, "name");
        NamespacedKey levelkey = new NamespacedKey(plugin, "level");
        container.set(namekey, PersistentDataType.STRING, name);
        container.set(healthkey, PersistentDataType.DOUBLE, health);
        container.set(damagekey, PersistentDataType.DOUBLE, damage);
        container.set(typekey, PersistentDataType.STRING, "archer");
        container.set(levelkey, PersistentDataType.INTEGER, level);
        skeleton.setCustomName(Utils.Chat(name + " &4" + health));
        skeleton.setCustomNameVisible(true);
    }

    public static void newBomber(LivingEntity creeper, double health, double damage, int level, String name) {
        Main plugin = Main.getPlugin(Main.class);
        PersistentDataContainer container = creeper.getPersistentDataContainer();
        NamespacedKey healthkey = new NamespacedKey(plugin, "health");
        NamespacedKey damagekey = new NamespacedKey(plugin, "damage");
        NamespacedKey typekey = new NamespacedKey(plugin, "undead");
        NamespacedKey namekey = new NamespacedKey(plugin, "name");
        NamespacedKey levelkey = new NamespacedKey(plugin, "level");
        container.set(namekey, PersistentDataType.STRING, name);
        container.set(healthkey, PersistentDataType.DOUBLE, health);
        container.set(damagekey, PersistentDataType.DOUBLE, damage);
        container.set(typekey, PersistentDataType.STRING, "bomber");
        container.set(levelkey, PersistentDataType.INTEGER, level);
        creeper.setCustomName(Utils.Chat(name + " &4" + health));
        creeper.setCustomNameVisible(true);
    }

    public static void newArachnid(LivingEntity spider, double health, double damage, int level, String name) {
        Main plugin = Main.getPlugin(Main.class);
        PersistentDataContainer container = spider.getPersistentDataContainer();
        NamespacedKey healthkey = new NamespacedKey(plugin, "health");
        NamespacedKey damagekey = new NamespacedKey(plugin, "damage");
        NamespacedKey typekey = new NamespacedKey(plugin, "undead");
        NamespacedKey namekey = new NamespacedKey(plugin, "name");
        NamespacedKey levelkey = new NamespacedKey(plugin, "level");
        container.set(namekey, PersistentDataType.STRING, name);
        container.set(healthkey, PersistentDataType.DOUBLE, health);
        container.set(damagekey, PersistentDataType.DOUBLE, damage);
        container.set(typekey, PersistentDataType.STRING, "arachnid");
        container.set(levelkey, PersistentDataType.INTEGER, level);
        spider.setCustomName(Utils.Chat(name + " &4" + health));
        spider.setCustomNameVisible(true);
    }

    public static void setArmor(Color color, LivingEntity zombie) {

        ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
        LeatherArmorMeta helmetmeta = (LeatherArmorMeta) helmet.getItemMeta();
        helmetmeta.setColor(color);
        helmet.setItemMeta(helmetmeta);
        zombie.getEquipment().setHelmet(helmet);

        ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
        LeatherArmorMeta chestmeta = (LeatherArmorMeta) chestplate.getItemMeta();
        chestmeta.setColor(color);
        chestplate.setItemMeta(chestmeta);
        zombie.getEquipment().setChestplate(chestplate);

        ItemStack pants = new ItemStack(Material.LEATHER_LEGGINGS);
        LeatherArmorMeta pantsmeta = (LeatherArmorMeta) pants.getItemMeta();
        pantsmeta.setColor(color);
        pants.setItemMeta(pantsmeta);
        zombie.getEquipment().setLeggings(pants);

        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
        LeatherArmorMeta bootsmeta = (LeatherArmorMeta) boots.getItemMeta();
        bootsmeta.setColor(color);
        boots.setItemMeta(bootsmeta);
        zombie.getEquipment().setBoots(boots);
    }
}
