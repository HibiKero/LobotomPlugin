package hibikero.lobotomplugin.BackEnd.Manager;


import hibikero.lobotomplugin.BackEnd.Listener.Player.SanListener;
import hibikero.lobotomplugin.BackEnd.Listener.Item.SanDetectorListener;
import hibikero.lobotomplugin.BackEnd.Listener.Entity.BodiPersistenceListener;
import hibikero.lobotomplugin.BackEnd.Listener.Entity.BodiDeathListener;
import hibikero.lobotomplugin.BackEnd.Listener.Entity.BodiShearListener;
import hibikero.lobotomplugin.BackEnd.Listener.Crafting.MultiblockCraftingListener;
import hibikero.lobotomplugin.BackEnd.Listener.Armor.DogFurChestplateListener;
import hibikero.lobotomplugin.LobotomPlugin;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import static org.bukkit.Bukkit.getServer;

public class ListenerRegisterManager {
    public static void registerListeners(LobotomPlugin plugin) {
        // 注册监听器
        registerListener(plugin, new SanListener());
        registerListener(plugin, new MultiblockCraftingListener());
        registerListener(plugin, new SanDetectorListener());
        registerListener(plugin, new BodiPersistenceListener(plugin));
        registerListener(plugin, new BodiDeathListener());
        registerListener(plugin, new BodiShearListener(plugin));
        plugin.getServer().getPluginManager().registerEvents(
            new DogFurChestplateListener(plugin), 
            plugin
        );
    }

    private static void registerListener(LobotomPlugin plugin, Listener listener) {
        plugin.getServer().getPluginManager().registerEvents(listener, plugin);
    }
} 