package hibikero.lobotomplugin.BackEnd.Listener.Entity;

import hibikero.lobotomplugin.BackEnd.Entities.Bodi.BodiWolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.entity.Wolf;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class BodiListener implements Listener {
    private final BodiWolf bodiWolf;

    public BodiListener(BodiWolf bodiWolf) {
        this.bodiWolf = bodiWolf;
    }

    @EventHandler
    public void onEntityTarget(EntityTargetEvent event) {
        if (event.getEntity() instanceof Wolf) {
            Wolf wolf = (Wolf) event.getEntity();
            if (wolf.equals(bodiWolf.getWolf())) {
                
            }
        }
    }
} 