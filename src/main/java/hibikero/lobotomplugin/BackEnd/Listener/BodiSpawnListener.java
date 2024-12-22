package hibikero.lobotomplugin.BackEnd.Listener;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkPopulateEvent;
import org.bukkit.block.Biome;
import org.bukkit.event.entity.EntityBreedEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import java.util.Random;

public class BodiSpawnListener implements Listener {
    private final Random random = new Random();

    @EventHandler
    public void onChunkPopulate(ChunkPopulateEvent event) {
        World world = event.getWorld();

        // 获取区块中心位置
        int centerX = event.getChunk().getX() * 16 + 8;
        int centerZ = event.getChunk().getZ() * 16 + 8;

        // 检查生物群系
        Location location = new Location(world, centerX, 0, centerZ);
        if (location.getBlock().getBiome() != Biome.DARK_FOREST) {
            return;
        }

        // 生成概率
        if (random.nextFloat() > 0.1) {
            return;
        }

        // 找到合适的生成位置（地表）
        int spawnY = world.getHighestBlockYAt(centerX, centerZ);
        Location spawnLocation = new Location(world, centerX, spawnY, centerZ);

        // 生成自定义狼
        Wolf customWolf = (Wolf) world.spawnEntity(spawnLocation, EntityType.WOLF);
        setupCustomWolf(customWolf);
    }

    // 设置自定义狼的属性
    private void setupCustomWolf(Wolf wolf) {
        wolf.setAngry(true);
        wolf.setCustomName("§c波迪");
        wolf.setCustomNameVisible(true);
        wolf.setHealth(40.0);
        wolf.setTamed(false); // 确保狼不是被驯服状态
        wolf.setAgeLock(true); // 锁定年龄，防止成长和繁殖
    }

    // 阻止玩家使用骨头驯服
    @EventHandler
    public void onPlayerInteract(PlayerInteractEntityEvent event) {
        if (event.getRightClicked() instanceof Wolf) {
            Wolf wolf = (Wolf) event.getRightClicked();
            if (wolf.getCustomName() != null && wolf.getCustomName().equals("§c波迪")) {
                event.setCancelled(true);
            }
        }
    }

    // 阻止繁殖
    @EventHandler
    public void onEntityBreed(EntityBreedEvent event) {
        if (event.getMother() instanceof Wolf && event.getFather() instanceof Wolf) {
            Wolf mother = (Wolf) event.getMother();
            Wolf father = (Wolf) event.getFather();
            
            if ((mother.getCustomName() != null && mother.getCustomName().equals("§c波迪")) ||
                (father.getCustomName() != null && father.getCustomName().equals("§c波迪"))) {
                event.setCancelled(true);
            }
        }
    }
}