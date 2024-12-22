package hibikero.lobotomplugin;

import hibikero.lobotomplugin.BackEnd.Command.SpawnCustomCommand;
import hibikero.lobotomplugin.BackEnd.Entities.Bodi.BodiEntity;
import hibikero.lobotomplugin.BackEnd.Entities.CustomEntityManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class LobotomPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        // 注册自定义生物
        CustomEntityManager.registerEntity(new BodiEntity());
        
        // 注册命令
        SpawnCustomCommand spawnCommand = new SpawnCustomCommand();
        getCommand("spawnmob").setExecutor(spawnCommand);
        getCommand("spawnmob").setTabCompleter(spawnCommand);
        
        getLogger().info("LobotomPlugin 已启用!");
    }

    @Override
    public void onDisable() {
        getLogger().info("LobotomPlugin 已禁用!");
    }
}
