package hibikero.lobotomplugin.BackEnd.Listener.Player;

import hibikero.lobotomplugin.BackEnd.System.San.SanDecayTask;
import hibikero.lobotomplugin.BackEnd.System.San.SanValueTool;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class SanListener implements Listener {
    
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        SanValueTool.initializePlayer(event.getPlayer());
        SanDecayTask.startSanDecayTask(event.getPlayer());
    }
    
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        SanValueTool.removePlayer(event.getPlayer());
    }
} 