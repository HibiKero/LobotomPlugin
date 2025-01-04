package hibikero.lobotomplugin.BackEnd.Entities;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.LivingEntity;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;

public class EntityPersistenceManager {
    private final Plugin plugin;
    private final Map<String, NamespacedKey> entityKeys;
    private static final String TYPE_KEY_SUFFIX = "_type";
    private static final String HEALTH_KEY_SUFFIX = "_health";

    public EntityPersistenceManager(Plugin plugin) {
        this.plugin = plugin;
        this.entityKeys = new HashMap<>();
    }

    /**
     * 注册一个自定义实体类型
     * @param entityId 实体的唯一标识符
     */
    public void registerEntityType(String entityId) {
        entityKeys.put(entityId + TYPE_KEY_SUFFIX, new NamespacedKey(plugin, entityId + TYPE_KEY_SUFFIX));
        entityKeys.put(entityId + HEALTH_KEY_SUFFIX, new NamespacedKey(plugin, entityId + HEALTH_KEY_SUFFIX));
    }

    /**
     * 标记实体为特定类型
     * @param entity 要标记的实体
     * @param entityId 实体类型的唯一标识符
     * @param maxHealth 实体的最大生命值
     */
    public void markEntity(LivingEntity entity, String entityId, double maxHealth) {
        PersistentDataContainer container = entity.getPersistentDataContainer();
        NamespacedKey typeKey = getTypeKey(entityId);
        NamespacedKey healthKey = getHealthKey(entityId);

        container.set(typeKey, PersistentDataType.BYTE, (byte) 1);
        container.set(healthKey, PersistentDataType.DOUBLE, maxHealth);

        entity.setMaxHealth(maxHealth);
        entity.setHealth(maxHealth);
    }

    /**
     * 保存实体的当前状态
     * @param entity 要保存的实体
     * @param entityId 实体类型的唯一标识符
     */
    public void saveEntityState(LivingEntity entity, String entityId) {
        if (!isCustomEntity(entity, entityId)) return;

        PersistentDataContainer container = entity.getPersistentDataContainer();
        NamespacedKey healthKey = getHealthKey(entityId);
        container.set(healthKey, PersistentDataType.DOUBLE, entity.getHealth());
    }

    /**
     * 恢复实体的状态
     * @param entity 要恢复的实体
     * @param entityId 实体类型的唯一标识符
     * @param maxHealth 实体的最大生命值
     * @return 是否成功恢复（如果不是自定义实体则返回false）
     */
    public boolean restoreEntityState(LivingEntity entity, String entityId, double maxHealth) {
        if (!isCustomEntity(entity, entityId)) return false;

        PersistentDataContainer container = entity.getPersistentDataContainer();
        NamespacedKey healthKey = getHealthKey(entityId);

        entity.setMaxHealth(maxHealth);
        Double savedHealth = container.get(healthKey, PersistentDataType.DOUBLE);
        if (savedHealth != null && savedHealth > 0) {
            entity.setHealth(Math.min(savedHealth, maxHealth));
        } else {
            entity.setHealth(maxHealth);
        }

        return true;
    }

    /**
     * 检查实体是否为指定的自定义类型
     * @param entity 要检查的实体
     * @param entityId 实体类型的唯一标识符
     * @return 是否为指定的自定义实体
     */
    public boolean isCustomEntity(LivingEntity entity, String entityId) {
        if (entity == null) return false;
        PersistentDataContainer container = entity.getPersistentDataContainer();
        NamespacedKey typeKey = getTypeKey(entityId);
        return container.has(typeKey, PersistentDataType.BYTE);
    }

    private NamespacedKey getTypeKey(String entityId) {
        return entityKeys.computeIfAbsent(entityId + TYPE_KEY_SUFFIX,
                key -> new NamespacedKey(plugin, entityId + TYPE_KEY_SUFFIX));
    }

    private NamespacedKey getHealthKey(String entityId) {
        return entityKeys.computeIfAbsent(entityId + HEALTH_KEY_SUFFIX,
                key -> new NamespacedKey(plugin, entityId + HEALTH_KEY_SUFFIX));
    }
} 