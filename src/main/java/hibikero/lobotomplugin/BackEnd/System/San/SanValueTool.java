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
        if (SanDataManager.hasData(player.getUniqueId())) {
            double storedSan = loadPlayerSanValue(player);
            setSanValue(player, storedSan);
        } else {
            setSanValue(player, DEFAULT_SAN);
        }
    }

    public static double getSanValue(Player player) {
        return playerSanValues.getOrDefault(player.getUniqueId(), loadPlayerSanValue(player));
    }

    public static void removePlayer(Player player) {
        if (playerSanValues.containsKey(player.getUniqueId())) {
            double currentSan = playerSanValues.get(player.getUniqueId());
            SanDataManager.saveSanValue(player.getUniqueId(), (int) currentSan);
        }
        playerSanValues.remove(player.getUniqueId());
    }

    public static void setSanValue(Player player, double value) {
        double sanValue = Math.min(Math.max(value, 0), MAX_SAN);
        playerSanValues.put(player.getUniqueId(), sanValue);
        SanDataManager.saveSanValue(player.getUniqueId(), (int) sanValue);
    }

    public static void increaseSanValue(Player player, double amount) {
        double currentSan = getSanValue(player);
        setSanValue(player, Math.min(currentSan + amount, MAX_SAN));
    }

    public static void reduceSanValue(Player player, double amount) {
        double currentSan = getSanValue(player);
        setSanValue(player, Math.max(currentSan - amount, 0));
    }

    public static double loadPlayerSanValue(Player player) {
        return SanDataManager.loadSanValue(player.getUniqueId());
    }
} 