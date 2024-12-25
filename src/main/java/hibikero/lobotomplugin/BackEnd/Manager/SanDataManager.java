package hibikero.lobotomplugin.BackEnd.Manager;

import hibikero.lobotomplugin.BackEnd.System.San.SanManager;
import hibikero.lobotomplugin.LobotomPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class SanDataManager {
    private static final String FILE_NAME = "playerSan.yml";
    private static File file;
    private static FileConfiguration config;

    // 初始化数据文件
    public static void init() {
        file = new File(LobotomPlugin.getInstance().getDataFolder(), FILE_NAME);
        if (!file.exists()) {
            LobotomPlugin.getInstance().saveResource(FILE_NAME, false);
        }
        config = YamlConfiguration.loadConfiguration(file);
    }

    // 保存玩家的SAN值
    public static void saveSanValue(UUID playerUUID, int value) {
        config.set("players." + playerUUID.toString(), value);
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 加载玩家的SAN值
    public static int loadSanValue(UUID playerUUID) {
        return config.getInt("players." + playerUUID.toString(), SanManager.DEFAULT_SAN);
    }

    // 检查玩家是否有保存的数据
    public static boolean hasData(UUID playerUUID) {
        return config.contains("players." + playerUUID.toString());
    }
} 