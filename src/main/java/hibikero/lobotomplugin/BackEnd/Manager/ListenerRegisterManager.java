package hibikero.lobotomplugin.BackEnd.Manager;

import hibikero.lobotomplugin.BackEnd.Listener.Entity.BodiEvent;
import hibikero.lobotomplugin.BackEnd.Listener.Player.SanListener;
import hibikero.lobotomplugin.BackEnd.Listener.Item.SanDetectorListener;
import hibikero.lobotomplugin.BackEnd.Listener.Crafting.MultiblockCraftingListener;
import hibikero.lobotomplugin.BackEnd.Entities.Bodi.BodiWolf;
import hibikero.lobotomplugin.LobotomPlugin;
import org.bukkit.event.Listener;

public class ListenerRegisterManager {
    public static void registerListeners(LobotomPlugin plugin) {
        // 创建 BodiWolf 实例
        BodiWolf bodiWolf = new BodiWolf(plugin, plugin.getServer().getWorlds().get(0), plugin.getServer().getWorlds().get(0).getSpawnLocation());

        // 注册监听器
        registerListener(plugin, new SanListener());
        registerListener(plugin, new MultiblockCraftingListener());
        registerListener(plugin, new SanDetectorListener());
        registerListener(plugin, new BodiEvent(bodiWolf));
    }

    private static void registerListener(LobotomPlugin plugin, Listener listener) {
        plugin.getServer().getPluginManager().registerEvents(listener, plugin);
    }
} 