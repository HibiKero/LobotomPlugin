package hibikero.lobotomplugin.BackEnd.Entities.Bodi;

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
    private Wolf wolf;
    private Map<Player, Integer> playerProximityTime; // 记录玩家在波迪附近的时间

    public BodiWolf(Plugin plugin, World world, Location location) {
        this.wolf = (Wolf) world.spawnEntity(location, EntityType.WOLF);
        wolf.setTamed(false); // 确保波迪不能被驯服
        wolf.setAngry(false); // 初始状态不愤怒
        wolf.setMaxHealth(BodiData.MAX_HEALTH); // 设置最大生命值
        wolf.setHealth(BodiData.MAX_HEALTH); // 设置当前生命值

        playerProximityTime = new HashMap<>(); // 初始化玩家时间记录

        // 启动定时任务
        startSanIncreaseTask(plugin);
    }

    public Wolf getWolf() {
        return wolf;
    }

    private void startSanIncreaseTask(Plugin plugin) {
        new BukkitRunnable() {
            @Override
            public void run() {
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
        }.runTaskTimer(plugin, 0, 20); // 使用插件实例
    }

    // 可以在这里添加波迪特有的行为和属性
}