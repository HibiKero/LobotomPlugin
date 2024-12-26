package hibikero.lobotomplugin.BackEnd.System.San;

import hibikero.lobotomplugin.BackEnd.Manager.SanDataManager;
import hibikero.lobotomplugin.LobotomPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SanValueTool {
    public static final int DEFAULT_SAN = 200;
    private static final int MAX_SAN = 200;
    private static final Map<UUID, Double> playerSanValues = new HashMap<>();


    public static void initializePlayer(Player player) {
        setSanValue(player, DEFAULT_SAN);
    }

    public static double getSanValue(Player player) {
        return playerSanValues.getOrDefault(player.getUniqueId(), (double) DEFAULT_SAN);
    }

    public static void removePlayer(Player player) {
        playerSanValues.remove(player.getUniqueId());
    }

    public static void setSanValue(Player player, double value) {
        double sanValue = Math.min(Math.max(value, 0), MAX_SAN);
        playerSanValues.put(player.getUniqueId(), sanValue);
        SanDataManager.saveSanValue(player.getUniqueId(), (int) sanValue); // 保存时向下取整
    }

    public static void increaseSanValue(Player player, double amount) {
        double currentSan = getSanValue(player);
        setSanValue(player, Math.min(currentSan + amount, MAX_SAN)); // 确保SAN值不超过上限
    }

    public static void reduceSanValue(Player player, double amount) {
        double currentSan = getSanValue(player);
        setSanValue(player, Math.max(currentSan - amount, 0)); // 确保SAN值不低于0
    }

    public static double loadPlayerSanValue(Player player) {
        File sanFile = new File(LobotomPlugin.getInstance().getDataFolder(), "playerSan.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(sanFile);

        // 获取玩家的 SAN 值，默认值为 DEFAULT_SAN
        return config.getDouble(player.getUniqueId().toString(), DEFAULT_SAN);
    }

} 