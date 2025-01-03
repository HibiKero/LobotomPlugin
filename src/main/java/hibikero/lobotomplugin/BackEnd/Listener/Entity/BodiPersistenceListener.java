package hibikero.lobotomplugin.BackEnd.Listener.Entity;

import hibikero.lobotomplugin.BackEnd.Entities.Bodi.BodiData;
import hibikero.lobotomplugin.BackEnd.Entities.Bodi.BodiWolf;
import hibikero.lobotomplugin.LobotomPlugin;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class BodiPersistenceListener implements Listener {
    private final NamespacedKey BODI_KEY;
    private final NamespacedKey BODI_HEALTH_KEY;
    private final LobotomPlugin plugin;

    public BodiPersistenceListener(LobotomPlugin plugin) {
        this.plugin = plugin;
        this.BODI_KEY = new NamespacedKey(plugin, "is_bodi");
        this.BODI_HEALTH_KEY = new NamespacedKey(plugin, "bodi_health");
    }

    @EventHandler
    public void onChunkLoad(ChunkLoadEvent event) {
        if (event == null || event.getChunk() == null) return;
        
        try {
            for (Entity entity : event.getChunk().getEntities()) {
                if (entity instanceof Wolf) {
                    Wolf wolf = (Wolf) entity;
                    PersistentDataContainer container = wolf.getPersistentDataContainer();
                    
                    if (container != null && container.has(BODI_KEY, PersistentDataType.BYTE)) {
                        // 恢复波迪的属性
                        wolf.setCustomName("波迪");
                        wolf.setCustomNameVisible(true);
                        wolf.setTamed(false);
                        
                        // 恢复生命值
                        Double savedHealth = container.get(BODI_HEALTH_KEY, PersistentDataType.DOUBLE);
                        wolf.setMaxHealth(BodiData.MAX_HEALTH);
                        wolf.setHealth(savedHealth != null && savedHealth > 0 ? savedHealth : BodiData.MAX_HEALTH);
                        
                        // 重新创建BodiWolf实例来管理这个实体
                        new BodiWolf(plugin, wolf);
                    }
                }
            }
        } catch (Exception e) {
            plugin.getLogger().warning("在加载波迪时发生错误: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @EventHandler
    public void onChunkUnload(ChunkUnloadEvent event) {
        if (event == null || event.getChunk() == null) return;
        
        try {
            for (Entity entity : event.getChunk().getEntities()) {
                if (entity instanceof Wolf) {
                    Wolf wolf = (Wolf) entity;
                    PersistentDataContainer container = wolf.getPersistentDataContainer();
                    
                    if (container != null && container.has(BODI_KEY, PersistentDataType.BYTE)) {
                        // 保存波迪的当前生命值
                        container.set(BODI_HEALTH_KEY, PersistentDataType.DOUBLE, wolf.getHealth());
                    }
                }
            }
        } catch (Exception e) {
            plugin.getLogger().warning("在保存波迪时发生错误: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event) {
        if (event == null || !(event.getEntity() instanceof Wolf)) return;
        
        try {
            Wolf wolf = (Wolf) event.getEntity();
            PersistentDataContainer container = wolf.getPersistentDataContainer();
            
            if (container != null && container.has(BODI_KEY, PersistentDataType.BYTE)) {
                // 确保新生成的波迪具有正确的属性
                wolf.setCustomName("波迪");
                wolf.setCustomNameVisible(true);
                wolf.setMaxHealth(BodiData.MAX_HEALTH);
                wolf.setHealth(BodiData.MAX_HEALTH);
                wolf.setTamed(false);
            }
        } catch (Exception e) {
            plugin.getLogger().warning("在处理波迪生成时发生错误: " + e.getMessage());
            e.printStackTrace();
        }
    }
} 