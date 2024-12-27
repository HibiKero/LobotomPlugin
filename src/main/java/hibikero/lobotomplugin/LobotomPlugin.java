package hibikero.lobotomplugin;

import hibikero.lobotomplugin.BackEnd.Manager.CommandRegisterManager;
import hibikero.lobotomplugin.BackEnd.Manager.EntityRegisterManager;
import hibikero.lobotomplugin.BackEnd.Manager.ListenerRegisterManager;
import hibikero.lobotomplugin.BackEnd.Config.EntityConfigReader;
import hibikero.lobotomplugin.BackEnd.Manager.SanDataManager;
import hibikero.lobotomplugin.BackEnd.Crafting.MultiblockCraftingManager;
import hibikero.lobotomplugin.BackEnd.Entities.Bodi.BodiWolf;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;

public final class LobotomPlugin extends JavaPlugin {
    private static LobotomPlugin instance;
    private BodiWolf bodiWolf;

    @Override
    public void onEnable() {
        instance = this;
        
        // 初始化SAN值数据管理器
        SanDataManager.init();
        
        // 初始化合成配方
        MultiblockCraftingManager.initRecipes();
        
        // 加载配置
        EntityConfigReader.loadConfig();
        
        // 使用管理器类进行注册
        EntityRegisterManager.registerEntities();
        CommandRegisterManager.registerCommands(this);
        ListenerRegisterManager.registerListeners(this);
        
        File sanFile = new File(getDataFolder(), "playerSan.yml");
        if (!sanFile.exists()) {
            saveResource("playerSan.yml", false); // 创建空文件
        }

        
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
