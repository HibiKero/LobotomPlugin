package hibikero.lobotomplugin.BackEnd.Entities.Bodi;

import hibikero.lobotomplugin.BackEnd.Entities.ICustomEntity;
import hibikero.lobotomplugin.BackEnd.System.San.SanValueTool;
import hibikero.lobotomplugin.LobotomPlugin;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Wolf;
import org.bukkit.entity.Skeleton;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
import org.bukkit.event.entity.EntityDeathEvent;
import hibikero.lobotomplugin.BackEnd.Items.DogFur;

import java.util.Random;

import static hibikero.lobotomplugin.BackEnd.Entities.Bodi.BodiData.MAX_HEALTH;

public class BodiEntity implements ICustomEntity{
    private static final NamespacedKey ENTITY_KEY = new NamespacedKey(LobotomPlugin.getInstance(), "custom_entity_id");

    private static Entity targetEntity; // 当前仇恨目标
    private static boolean isAngry = false; // 波迪的愤怒状态

    @Override
    public String getIdentifier() {
        return "bodi";
    }

    @Override
    public Entity spawn(Location location) {
        Wolf wolf = (Wolf) location.getWorld().spawnEntity(location, EntityType.WOLF);
        setupCustomWolf(wolf);
        return wolf;
    }

    private void setupCustomWolf(Wolf wolf) {
        wolf.setMaxHealth(MAX_HEALTH); // 设置最大生命值
        wolf.setHealth(MAX_HEALTH); // 设置当前生命值
        wolf.setAngry(true);
        wolf.setCustomName("§c波迪");
        wolf.setCustomNameVisible(true);
        wolf.setTamed(false);
        wolf.setAgeLock(true);
        
        // 添加自定义标记
        wolf.getPersistentDataContainer().set(ENTITY_KEY, PersistentDataType.STRING, getIdentifier());
    }

    public static Entity getTargetEntity() {
        return targetEntity;
    }

    public void setTargetEntity(Entity targetEntity) {
        this.targetEntity = targetEntity;
    }

    public static boolean isAngry() {
        return isAngry;
    }

    public void setAngry(boolean angry) {
        isAngry = angry;
    }


    public static boolean isBodi(Entity entity) {
        return entity instanceof Wolf && 
               entity.getPersistentDataContainer().has(ENTITY_KEY, PersistentDataType.STRING) &&
               entity.getPersistentDataContainer().get(ENTITY_KEY, PersistentDataType.STRING).equals("bodi");
    }
} 