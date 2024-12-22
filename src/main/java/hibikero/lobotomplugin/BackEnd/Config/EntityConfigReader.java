package hibikero.lobotomplugin.BackEnd.Config;

import hibikero.lobotomplugin.LobotomPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class EntityConfigReader {
    private static FileConfiguration config;
    private static final String CONFIG_NAME = "Entity.yml";

    public static void loadConfig() {
        File configFile = new File(LobotomPlugin.getInstance().getDataFolder(), CONFIG_NAME);
        if (!configFile.exists()) {
            LobotomPlugin.getInstance().saveResource(CONFIG_NAME, false);
        }
        config = YamlConfiguration.loadConfiguration(configFile);
    }

    public static double getSpawnChance(String entityId) {
        if (config == null) {
            loadConfig();
        }
        return config.getDouble("Entities." + entityId + ".SpawnChance", 0.1);
    }
} 