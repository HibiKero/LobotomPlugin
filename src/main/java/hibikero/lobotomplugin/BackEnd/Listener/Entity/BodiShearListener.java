package hibikero.lobotomplugin.BackEnd.Listener.Entity;

import hibikero.lobotomplugin.BackEnd.Items.DogFur;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public class BodiShearListener implements Listener {
    private static final long COOLDOWN_TIME = 5 * 60 * 1000; // 5分钟的冷却时间（毫秒）
    private final HashMap<UUID, Long> cooldowns = new HashMap<>();
    private final NamespacedKey lastShearTimeKey;

    public BodiShearListener(JavaPlugin plugin) {
        this.lastShearTimeKey = new NamespacedKey(plugin, "last_shear_time");
    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        // 检查是否是对狼使用物品
        if (!(event.getRightClicked() instanceof Wolf)) {
            return;
        }

        Wolf wolf = (Wolf) event.getRightClicked();
        // 检查是否是波迪
        if (!wolf.getCustomName().equals("波迪")) {
            return;
        }

        Player player = event.getPlayer();
        // 检查是否使用剪刀
        if (player.getInventory().getItemInMainHand().getType() != Material.SHEARS) {
            return;
        }

        // 检查冷却时间
        Long lastShearTime = wolf.getPersistentDataContainer().get(lastShearTimeKey, PersistentDataType.LONG);
        long currentTime = System.currentTimeMillis();
        
        if (lastShearTime != null) {
            long timeSinceLastShear = currentTime - lastShearTime;
            if (timeSinceLastShear < COOLDOWN_TIME) {
                long remainingCooldown = (COOLDOWN_TIME - timeSinceLastShear) / 1000; // 转换为秒
                player.sendMessage("§c波迪还没准备好被剪，请等待" + remainingCooldown + "秒。");
                event.setCancelled(true);
                return;
            }
        }

        // 更新最后剪毛时间
        wolf.getPersistentDataContainer().set(lastShearTimeKey, PersistentDataType.LONG, currentTime);

        // 掉落1个狗毛
        ItemStack dogFur = DogFur.create();
        dogFur.setAmount(1);
        wolf.getWorld().dropItemNaturally(wolf.getLocation(), dogFur);

        // 设置波迪对该玩家的仇恨
        wolf.setAngry(true);
        wolf.setTarget(player);

        // 取消事件以防止原版剪刀行为
        event.setCancelled(true);
    }
} 