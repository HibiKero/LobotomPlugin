package hibikero.lobotomplugin.BackEnd.Entities.Bodi;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Wolf;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class BodiEntity {
    private BodiWolf bodiWolf;

    public BodiEntity(Plugin plugin) {
        // 初始化波迪的特性
    }

    public void spawn(Location location) {
        World world = location.getWorld();
        if (world != null) {
            bodiWolf = new BodiWolf(Bukkit.getPluginManager().getPlugin("LobotomPlugin"), world, location);
            bodiWolf.getWolf().setCustomName("波迪"); // 设置波迪的名称
            bodiWolf.getWolf().setCustomNameVisible(true); // 显示名称
        }
    }



    // 其他波迪的特性和行为方法
}