package hibikero.lobotomplugin.BackEnd.Listener.Entity;

import hibikero.lobotomplugin.BackEnd.Entities.Bodi.BodiEntity;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityBreedEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class BodiListener implements Listener {
    
    @EventHandler
    public void onPlayerInteract(PlayerInteractEntityEvent event) {
        if (BodiEntity.isBodi(event.getRightClicked())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityBreed(EntityBreedEvent event) {
        if (BodiEntity.isBodi(event.getMother()) || BodiEntity.isBodi(event.getFather())) {
            event.setCancelled(true);
        }
    }
}