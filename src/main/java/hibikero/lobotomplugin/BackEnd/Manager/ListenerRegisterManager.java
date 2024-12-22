package hibikero.lobotomplugin.BackEnd.Manager;

import hibikero.lobotomplugin.BackEnd.Listener.Entity.BodiListener;
import hibikero.lobotomplugin.BackEnd.Listener.Spawn.BodiSpawnListener;
import hibikero.lobotomplugin.LobotomPlugin;
import org.bukkit.event.Listener;

public class ListenerRegisterManager {
    public static void registerListeners(LobotomPlugin plugin) {
        // 注册监听器
        registerListener(plugin, new BodiListener());
        registerListener(plugin, new BodiSpawnListener());
    }

    private static void registerListener(LobotomPlugin plugin, Listener listener) {
        plugin.getServer().getPluginManager().registerEvents(listener, plugin);
    }
} 