package hibikero.lobotomplugin.BackEnd.Manager;

import hibikero.lobotomplugin.BackEnd.Manager.SanDataManager;
import org.bukkit.entity.Player;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SanManager {
    public static final int DEFAULT_SAN = 200;
    private static final int MAX_SAN = 200;
    private static final Map<UUID, Integer> playerSanValues = new HashMap<>();
    
    // 获取玩家的SAN值
    public static int getSanValue(Player player) {
        UUID playerUUID = player.getUniqueId();
        if (!playerSanValues.containsKey(playerUUID)) {
            // 如果内存中没有,从文件加载
            int savedValue = SanDataManager.loadSanValue(playerUUID);
            playerSanValues.put(playerUUID, savedValue);
        }
        return playerSanValues.get(playerUUID);
    }
    
    // 设置玩家的SAN值
    public static void setSanValue(Player player, int value) {
        int sanValue = Math.min(Math.max(value, 0), MAX_SAN);
        UUID playerUUID = player.getUniqueId();
        playerSanValues.put(playerUUID, sanValue);
        // 保存到文件
        SanDataManager.saveSanValue(playerUUID, sanValue);
    }
    
    // 增加玩家的SAN值
    public static void addSanValue(Player player, int amount) {
        int currentSan = getSanValue(player);
        setSanValue(player, currentSan + amount);
    }
    
    // 减少玩家的SAN值
    public static void reduceSanValue(Player player, int amount) {
        int currentSan = getSanValue(player);
        setSanValue(player, currentSan - amount);
    }
    
    // 初始化玩家的SAN值
    public static void initializePlayer(Player player) {
        UUID playerUUID = player.getUniqueId();
        if (!SanDataManager.hasData(playerUUID)) {
            setSanValue(player, DEFAULT_SAN);
        } else {
            int savedValue = SanDataManager.loadSanValue(playerUUID);
            playerSanValues.put(playerUUID, savedValue);
        }
    }
    
    // 移除玩家的SAN值记录
    public static void removePlayer(Player player) {
        playerSanValues.remove(player.getUniqueId());
    }
    
    // 获取最大SAN值
    public static int getMaxSan() {
        return MAX_SAN;
    }
} 