package hibikero.lobotomplugin.BackEnd.Listener.Player;

import hibikero.lobotomplugin.BackEnd.Manager.SanManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class SanListener implements Listener {
    
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        SanManager.initializePlayer(event.getPlayer());
    }
    
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        SanManager.removePlayer(event.getPlayer());
    }
} 