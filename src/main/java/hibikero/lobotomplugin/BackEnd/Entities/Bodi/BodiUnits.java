package hibikero.lobotomplugin.BackEnd.Entities.Bodi;

import hibikero.lobotomplugin.BackEnd.System.San.SanValueTool;
import hibikero.lobotomplugin.LobotomPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.Sound;

public class BodiUnits {
    static Entity targetEntity = BodiEntity.getTargetEntity();
    static Boolean isAngry = BodiEntity.isAngry();
    public static void applySpeedEffect(Wolf wolf) {
        wolf.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 2, true, false)); // 施加速度2效果
        new BukkitRunnable() {
            @Override
            public void run() {
                if (targetEntity == null || targetEntity.isDead() || wolf.getLocation().distance(targetEntity.getLocation()) > 30) {
                    wolf.removePotionEffect(PotionEffectType.SPEED); // 移除速度效果
                    cancel(); // 停止任务
                }
            }
        }.runTaskTimer(LobotomPlugin.getInstance(), 0, 20); // 每20个tick（1秒）检查一次
    }

    public static void startHowlingAndSanDecrease(Wolf wolf) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (wolf.isAngry()) {
                    // 播放狼的嚎叫声音
                    wolf.getWorld().playSound(wolf.getLocation(), Sound.ENTITY_WOLF_HOWL, 1.0f, 1.0f);
                    
                    // 这里可以添加波迪的嚎叫音效
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        if (player.getWorld().equals(wolf.getWorld()) && player.getLocation().distance(wolf.getLocation()) <= 20) {
                            // 附近20米内的玩家SAN值下降0.7
                            SanValueTool.reduceSanValue(player, 0.7);
                        }
                    }
                } else {
                    cancel(); // 如果不再愤怒，停止任务
                }
            }
        }.runTaskTimer(LobotomPlugin.getInstance(), 0, 100); // 每20个tick（1秒）执行一次
    }
}
