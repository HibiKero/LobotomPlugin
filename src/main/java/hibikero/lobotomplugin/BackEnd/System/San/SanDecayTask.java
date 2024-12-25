package hibikero.lobotomplugin.BackEnd.System.San;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class SanDecayTask {
    public static void startSanDecayTask(Player player) {
        new BukkitRunnable() {
            @Override
            public void run() {
                // 每秒检测SAN
                PerSecondSan.SanHunger(player);
                PerSecondSan.SanNight(player);

                // 警告SAN值过低
                SanAlert.checkSanAlert(player);
                // 检查幻听
                SanPhonism.checkHearingVoices(player);
                // 检查极端事件
                ExtremeSanEvent.checkExtremeEvent(player);
            }
        }.runTaskTimer(Bukkit.getPluginManager().getPlugin("LobotomPlugin"), 0, 20); // 每20个tick（1秒）执行一次
    }
} 