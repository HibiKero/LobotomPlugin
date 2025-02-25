package hibikero.lobotomplugin.BackEnd.System.San;

import hibikero.lobotomplugin.BackEnd.Manager.SanDataManager;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SanManager {
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
}