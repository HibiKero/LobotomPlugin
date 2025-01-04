package hibikero.lobotomplugin.BackEnd.Listener.Entity;

import hibikero.lobotomplugin.BackEnd.Entities.Bodi.BodiWolf;
import hibikero.lobotomplugin.BackEnd.Items.DogFur;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class BodiDeathListener implements Listener {
    private static final Random random = new Random();

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (!(event.getEntity() instanceof Wolf)) {
            return;
        }

        Wolf wolf = (Wolf) event.getEntity();
        // 检查是否是波迪
        if (!wolf.getCustomName().equals("波迪")) {
            return;
        }

        // 获取击杀者的抢夺等级
        int lootingLevel = 0;
        if (wolf.getKiller() instanceof Player) {
            Player killer = wolf.getKiller();
            if (killer.getInventory().getItemInMainHand() != null) {
                lootingLevel = killer.getInventory().getItemInMainHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_MOBS);
            }
        }

        // 计算掉落数量（1-3个，受抢夺影响）
        int baseAmount = random.nextInt(3) + 1; // 1-3个
        int bonusAmount = lootingLevel > 0 ? random.nextInt(lootingLevel) : 0;
        int totalAmount = baseAmount + bonusAmount;

        // 创建并掉落狗毛
        ItemStack dogFur = DogFur.create();
        dogFur.setAmount(totalAmount);
        event.getDrops().add(dogFur);
    }
} 