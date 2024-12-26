package hibikero.lobotomplugin.BackEnd.System.San;

import hibikero.lobotomplugin.LobotomPlugin;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.Location;


public class ExtremeSanEvent {

    public static void checkExtremeEvent(Player player) {
        double currentSan = SanValueTool.getSanValue(player);
        if (currentSan < 5) {
            // 施加虚弱、挖掘疲劳、饥饿、中毒和失明效果
            player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 200, 1, true, false));
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 200, 1, true, false));
            player.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 200, 1, true, false));
            player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 200, 1, true, false));
            player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 200, 1, true, false)); // 施加失明效果
            player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 200, 1, true, false)); // 添加反胃效果

            // 角色随机移动
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (SanValueTool.getSanValue(player) < 17) {
                        // 随机生成一个角度
                        float randomYaw = (float) (Math.random() * 360);
                        // 设置玩家的朝向
                        Location location = player.getLocation();
                        location.setYaw(randomYaw);
                        location.setPitch(player.getLocation().getPitch());
                        player.teleport(location);
                        // 向前移动
                        player.setVelocity(player.getLocation().getDirection().multiply(0.5)); // 向前移动
                    } else {
                        cancel(); // 当SAN值大于17时停止
                    }
                }
            }.runTaskTimer(LobotomPlugin.getInstance(), 0, 20); // 每20个tick（1秒）执行一次

            // SAN值每秒增加1
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (SanValueTool.getSanValue(player) < 17) {
                        SanValueTool.increaseSanValue(player, 1);
                    } else {
                        cancel(); // 当SAN值大于17时停止
                    }
                }
            }.runTaskTimer(LobotomPlugin.getInstance(), 0, 20); // 每20个tick（1秒）执行一次
        }
    }
}