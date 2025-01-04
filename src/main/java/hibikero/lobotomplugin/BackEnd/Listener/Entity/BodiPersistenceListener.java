package hibikero.lobotomplugin.BackEnd.Listener.Entity;

import hibikero.lobotomplugin.BackEnd.Entities.Bodi.BodiData;
import hibikero.lobotomplugin.BackEnd.Entities.Bodi.BodiWolf;
import hibikero.lobotomplugin.BackEnd.Entities.EntityPersistenceManager;
import hibikero.lobotomplugin.LobotomPlugin;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.event.entity.EntitySpawnEvent;

public class BodiPersistenceListener implements Listener {
    private final LobotomPlugin plugin;
    private final EntityPersistenceManager persistenceManager;

    public BodiPersistenceListener(LobotomPlugin plugin) {
        this.plugin = plugin;
        this.persistenceManager = new EntityPersistenceManager(plugin);
        this.persistenceManager.registerEntityType(BodiWolf.ENTITY_ID);
    }

    @EventHandler
    public void onChunkLoad(ChunkLoadEvent event) {
        if (event == null || event.getChunk() == null) return;
        
        try {
            for (Entity entity : event.getChunk().getEntities()) {
                if (entity instanceof Wolf) {
                    Wolf wolf = (Wolf) entity;
                    if (persistenceManager.isCustomEntity(wolf, BodiWolf.ENTITY_ID)) {
                        // 恢复波迪的属性
                        wolf.setCustomName("波迪");
                        wolf.setCustomNameVisible(true);
                        wolf.setTamed(false);
                        
                        // 恢复状态并创建新的BodiWolf实例
                        if (persistenceManager.restoreEntityState(wolf, BodiWolf.ENTITY_ID, BodiData.MAX_HEALTH)) {
                            new BodiWolf(plugin, wolf);
                        }
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
                    if (persistenceManager.isCustomEntity(wolf, BodiWolf.ENTITY_ID)) {
                        persistenceManager.saveEntityState(wolf, BodiWolf.ENTITY_ID);
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
            if (persistenceManager.isCustomEntity(wolf, BodiWolf.ENTITY_ID)) {
                wolf.setCustomName("波迪");
                wolf.setCustomNameVisible(true);
                wolf.setTamed(false);
                persistenceManager.restoreEntityState(wolf, BodiWolf.ENTITY_ID, BodiData.MAX_HEALTH);
            }
        } catch (Exception e) {
            plugin.getLogger().warning("在处理波迪生成时发生错误: " + e.getMessage());
            e.printStackTrace();
        }
    }
} 