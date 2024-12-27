package hibikero.lobotomplugin.BackEnd.Listener.Entity;

import hibikero.lobotomplugin.BackEnd.Entities.Bodi.BodiData;
import hibikero.lobotomplugin.BackEnd.Entities.Bodi.BodiWolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.entity.Wolf;
import org.bukkit.entity.Player;

import static org.bukkit.Bukkit.getLogger;

public class BodiEvent implements Listener {
    private BodiWolf bodiWolf;

    public BodiEvent(BodiWolf bodiWolf) {
        this.bodiWolf = bodiWolf;
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {

        
    }
}

