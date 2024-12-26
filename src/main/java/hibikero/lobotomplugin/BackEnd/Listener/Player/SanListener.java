package hibikero.lobotomplugin.BackEnd.Listener.Player;

import hibikero.lobotomplugin.BackEnd.System.San.SanDecayTask;
import hibikero.lobotomplugin.BackEnd.System.San.SanValueTool;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.entity.Player;

public class SanListener implements Listener {
    
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        double storedSanValue = SanValueTool.loadPlayerSanValue(player);
        SanValueTool.setSanValue(player, storedSanValue);
        SanDecayTask.startSanDecayTask(player);
    }
    
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        SanValueTool.removePlayer(event.getPlayer());
    }
} 