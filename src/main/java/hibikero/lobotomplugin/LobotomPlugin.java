package hibikero.lobotomplugin;

import hibikero.lobotomplugin.BackEnd.Manager.CommandRegisterManager;
import hibikero.lobotomplugin.BackEnd.Manager.EntityRegisterManager;
import hibikero.lobotomplugin.BackEnd.Manager.ListenerRegisterManager;
import hibikero.lobotomplugin.BackEnd.Config.EntityConfigReader;
import org.bukkit.plugin.java.JavaPlugin;

public final class LobotomPlugin extends JavaPlugin {
    private static LobotomPlugin instance;

    @Override
    public void onEnable() {
        instance = this;
        
        // 加载配置
        EntityConfigReader.loadConfig();
        
        // 使用管理器类进行注册
        EntityRegisterManager.registerEntities();
        CommandRegisterManager.registerCommands(this);
        ListenerRegisterManager.registerListeners(this);
        
        getLogger().info("LobotomPlugin 已启用!");
    }

    @Override
    public void onDisable() {
        getLogger().info("LobotomPlugin 已禁用!");
    }

    public static LobotomPlugin getInstance() {
        return instance;
    }
}
