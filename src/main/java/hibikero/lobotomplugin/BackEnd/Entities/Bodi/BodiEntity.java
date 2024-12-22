package hibikero.lobotomplugin.BackEnd.Entities.Bodi;

import hibikero.lobotomplugin.BackEnd.Entities.ICustomEntity;
import hibikero.lobotomplugin.LobotomPlugin;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Wolf;
import org.bukkit.persistence.PersistentDataType;

public class BodiEntity implements ICustomEntity {
    private static final NamespacedKey ENTITY_KEY = new NamespacedKey(LobotomPlugin.getInstance(), "custom_entity_id");

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
        wolf.setAngry(true);
        wolf.setCustomName("§c波迪");
        wolf.setCustomNameVisible(true);
        wolf.setHealth(40.0);
        wolf.setTamed(false);
        wolf.setAgeLock(true);
        
        // 添加自定义标记
        wolf.getPersistentDataContainer().set(ENTITY_KEY, PersistentDataType.STRING, getIdentifier());
    }

    // 静态方法用于检查实体是否为波迪
    public static boolean isBodi(Entity entity) {
        return entity instanceof Wolf && 
               entity.getPersistentDataContainer().has(ENTITY_KEY, PersistentDataType.STRING) &&
               entity.getPersistentDataContainer().get(ENTITY_KEY, PersistentDataType.STRING).equals("bodi");
    }
} 