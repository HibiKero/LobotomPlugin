package hibikero.lobotomplugin.BackEnd.System.San;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class PerSecondSan {
    public static void SanHunger(Player player){
        int hungerLevel = player.getFoodLevel();
        if (hungerLevel > 19) {
            SanValueTool.increaseSanValue(player, 0.15); // 恢复0.15点SAN值
        } else if (hungerLevel < 10) {
            SanValueTool.reduceSanValue(player, 0.1);
        }
        if (hungerLevel < 6) {
            SanValueTool.reduceSanValue(player, 0.5);
        }
    }

    public static void SanNight(Player player){
        if (Bukkit.getWorlds().get(0).getTime() >= 13000 && Bukkit.getWorlds().get(0).getTime() <= 23000) {
            Location playerLocation = player.getLocation();
            if (playerLocation.getBlock().getLightLevel() <= 8) {
                SanValueTool.reduceSanValue(player, 0.3); // 每秒减少0.1点SAN值
            }
        }
    }
}
