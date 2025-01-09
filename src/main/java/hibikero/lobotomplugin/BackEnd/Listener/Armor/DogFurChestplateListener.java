package hibikero.lobotomplugin.BackEnd.Listener.Armor;

import hibikero.lobotomplugin.BackEnd.System.San.SanValueTool;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class DogFurChestplateListener implements Listener {
    
    public DogFurChestplateListener(Plugin plugin) {
        // 每秒检查一次
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    checkAndApplyArmorEffect(player);
                }
            }
        }.runTaskTimer(plugin, 0L, 20L); // 20 ticks = 1 second
    }

    private void checkAndApplyArmorEffect(Player player) {
        ItemStack chestplate = player.getInventory().getChestplate();
        if (chestplate == null) return;

        // 检查是否是波迪毛皮胸甲
        ItemMeta meta = chestplate.getItemMeta();
        if (meta == null || !meta.hasDisplayName() || 
            !meta.getDisplayName().equals("§6波迪的毛皮胸甲")) {
            return;
        }

        // 检查玩家的SAN值
        double currentSan = SanValueTool.getSanValue(player);
        if (currentSan < 120) {
            // 增加SAN值
            SanValueTool.setSanValue(player, currentSan + 0.2);

            // 减少耐久度
            if (meta instanceof Damageable) {
                Damageable damageable = (Damageable) meta;
                damageable.setDamage(damageable.getDamage() + 1);
                chestplate.setItemMeta(meta);
            }
        }
    }
} 