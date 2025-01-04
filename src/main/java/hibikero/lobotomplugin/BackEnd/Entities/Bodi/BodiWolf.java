package hibikero.lobotomplugin.BackEnd.Entities.Bodi;

import hibikero.lobotomplugin.BackEnd.Entities.EntityPersistenceManager;
import hibikero.lobotomplugin.BackEnd.System.San.SanValueTool;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.entity.EntityType;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.plugin.Plugin;
import org.bukkit.Material;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BodiWolf {
    private final Wolf wolf;
    private final EntityPersistenceManager persistenceManager;
    private Map<Player, Integer> playerProximityTime;
    public static final String ENTITY_ID = "bodi_wolf";

    public BodiWolf(Plugin plugin, World world, Location location) {
        this.wolf = (Wolf) world.spawnEntity(location, EntityType.WOLF);
        this.persistenceManager = new EntityPersistenceManager(plugin);
        this.persistenceManager.registerEntityType(ENTITY_ID);
        initializeWolf(plugin);
    }

    public BodiWolf(Plugin plugin, Wolf existingWolf) {
        this.wolf = existingWolf;
        this.persistenceManager = new EntityPersistenceManager(plugin);
        this.persistenceManager.registerEntityType(ENTITY_ID);
        initializeWolf(plugin);
    }

    private void initializeWolf(Plugin plugin) {
        wolf.setTamed(false);
        wolf.setAngry(false);
        wolf.setCustomName("波迪");
        wolf.setCustomNameVisible(true);
        
        // 使用持久化管理器标记和恢复实体状态
        if (!persistenceManager.isCustomEntity(wolf, ENTITY_ID)) {
            persistenceManager.markEntity(wolf, ENTITY_ID, BodiData.MAX_HEALTH);
        } else {
            persistenceManager.restoreEntityState(wolf, ENTITY_ID, BodiData.MAX_HEALTH);
        }
        
        playerProximityTime = new HashMap<>();
        startSanIncreaseTask(plugin);
    }

    public Wolf getWolf() {
        return wolf;
    }

    public boolean isBodi() {
        return persistenceManager.isCustomEntity(wolf, ENTITY_ID);
    }

    private void startSanIncreaseTask(Plugin plugin) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!wolf.isValid() || wolf.isDead()) {
                    this.cancel();
                    return;
                }

                // 每次更新时保存状态
                persistenceManager.saveEntityState(wolf, ENTITY_ID);

                if (wolf.getTarget() != null) {
                    wolf.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 2, false, false));
                    wolf.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 2, false, false));
                } else {
                    wolf.removePotionEffect(PotionEffectType.SPEED);
                    wolf.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
                }

                // 获取波迪的位置
                Location bodiLocation = wolf.getLocation();
                // 查找10米内的玩家
                List<Player> nearbyPlayers = Bukkit.getOnlinePlayers().stream()
                        .filter(player -> player.getLocation().distance(bodiLocation) <= 10)
                        .collect(Collectors.toList());

                // 为每个玩家增加SAN值
                for (Player player : nearbyPlayers) {
                    double currentSan = SanValueTool.getSanValue(player);
                    SanValueTool.setSanValue(player, currentSan + 0.2); // 增加0.2的SAN值

                    // 记录玩家在波迪附近的时间
                    playerProximityTime.put(player, playerProximityTime.getOrDefault(player, 0) + 1);

                    // 如果玩家在波迪附近超过10秒，施加缓慢效果
                    if (playerProximityTime.get(player) >= 10) {
                        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 1, true, false));
                    }

                    if (playerProximityTime.get(player) >= 15) {
                        wolf.setTarget(player);
                    }

                    // 检查玩家是否持有骨头
                    if (player.getInventory().contains(Material.BONE)) {
                        wolf.setTarget(player); // 设置目标为持有骨头的玩家
                    }
                }

                // 检查不在范围内的玩家，重置他们的时间和效果
                for (Player player : playerProximityTime.keySet().toArray(new Player[0])) {
                    if (!nearbyPlayers.contains(player)) {
                        playerProximityTime.remove(player); // 移除不在范围内的玩家
                        player.removePotionEffect(PotionEffectType.SLOW); // 移除缓慢效果
                    }
                }

                // 检查波迪的目标
                if (wolf.getTarget() != null) {
                    if (wolf.getTarget() instanceof Player) { // 确保目标是玩家
                        Player targetPlayer = (Player) wolf.getTarget(); // 进行强制转换
                        if (targetPlayer.isDead() || targetPlayer.getLocation().distance(bodiLocation) > 30) {
                            wolf.setTarget(null); // 清除目标
                        }
                    } else {
                        wolf.setTarget(null); // 如果目标不是玩家，清除目标
                    }
                }
            }
        }.runTaskTimer(plugin, 0, 20);
    }

    // 可以在这里添加波迪特有的行为和属性
}