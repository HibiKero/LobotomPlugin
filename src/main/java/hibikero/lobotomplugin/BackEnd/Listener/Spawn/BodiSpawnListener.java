package hibikero.lobotomplugin.BackEnd.Listener.Spawn;

import hibikero.lobotomplugin.BackEnd.Entities.Bodi.BodiEntity;
import hibikero.lobotomplugin.BackEnd.Config.EntityConfigReader;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkPopulateEvent;

import java.util.Random;

public class BodiSpawnListener implements Listener {
    private final Random random = new Random();
    private final BodiEntity bodiEntity;

    public BodiSpawnListener() {
        this.bodiEntity = new BodiEntity();
    }

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

        // 从配置文件获取生成概率
        double spawnChance = EntityConfigReader.getSpawnChance("Bodi");
        if (random.nextFloat() > spawnChance) {
            return;
        }

        // 找到合适的生成位置（地表）
        int spawnY = world.getHighestBlockYAt(centerX, centerZ);
        Location spawnLocation = new Location(world, centerX, spawnY, centerZ);

        // 使用BodiEntity来生成
        bodiEntity.spawn(spawnLocation);
    }
} 