package hibikero.lobotomplugin.BackEnd.Entities.Bodi;

import hibikero.lobotomplugin.BackEnd.Entities.ICustomEntity;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Wolf;

public class BodiEntity implements ICustomEntity {
    @Override
    public String getIdentifier() {
        return "bodi";
    }

    @Override
    public Entity spawn(Location location) {
        Wolf wolf = (Wolf) location.getWorld().spawnEntity(location, EntityType.WOLF);
        setupCustomWolf(wolf);
        return wolf;
    }

    private void setupCustomWolf(Wolf wolf) {
        wolf.setAngry(true);
        wolf.setCustomName("§c波迪");
        wolf.setCustomNameVisible(true);
        wolf.setHealth(40.0);
        wolf.setTamed(false);
        wolf.setAgeLock(true);
    }
} 