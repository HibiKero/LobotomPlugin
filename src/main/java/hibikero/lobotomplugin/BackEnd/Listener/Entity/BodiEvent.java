package hibikero.lobotomplugin.BackEnd.Listener.Entity;

import hibikero.lobotomplugin.BackEnd.Entities.Bodi.BodiData;
import hibikero.lobotomplugin.BackEnd.Entities.Bodi.BodiEntity;
import hibikero.lobotomplugin.BackEnd.Items.DogFur;
import hibikero.lobotomplugin.BackEnd.System.San.SanValueTool;
import hibikero.lobotomplugin.LobotomPlugin;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

import static hibikero.lobotomplugin.BackEnd.Entities.Bodi.BodiUnits.*;
import static hibikero.lobotomplugin.BackEnd.Entities.Bodi.BodiData.*;
import static hibikero.lobotomplugin.BackEnd.Entities.Bodi.BodiEntity.isBodi;

public class BodiEvent  implements Listener {



    Entity targetEntity = BodiEntity.getTargetEntity();
    Boolean isAngry = BodiEntity.isAngry();

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        Entity entity = event.getEntity();
        if (isBodi(entity)) { // 检查是否是Bodi实体
            BodiEntity bodi = (BodiEntity) entity; 
            // 设置仇恨目标为攻击者
            targetEntity = event.getDamager();
            isAngry = true; // 设置波迪为愤怒状态
            event.setDamage(BodiData.ATTACK_DAMAGE); 
            applySpeedEffect((Wolf) bodi); // 施加速度效果
            startHowlingAndSanDecrease((Wolf) bodi); // 开始嚎叫和SAN值下降
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Location playerLocation = player.getLocation();

        for (Entity entity : player.getWorld().getNearbyEntities(playerLocation, LOCK_DISTANCE, LOCK_DISTANCE, LOCK_DISTANCE)) {
            if (isBodi(entity)) {
                // 如果玩家靠近波迪，恢复SAN值
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if (SanValueTool.getSanValue(player) < 200) { // 确保SAN值不超过上限
                            SanValueTool.increaseSanValue(player, 0.1);
                        } else {
                            cancel(); // 当SAN值达到上限时停止
                        }
                    }
                }.runTaskTimer(LobotomPlugin.getInstance(), 0, 20); // 每20个tick（1秒）执行一次

                // 检查玩家的视角
                new BukkitRunnable() {
                    int ticks = 0;

                    @Override
                    public void run() {
                        if (player.getWorld().getNearbyEntities(playerLocation, LOCK_DISTANCE, LOCK_DISTANCE, LOCK_DISTANCE).contains(entity)) {
                            if (player.getTargetBlock(null, 100).getLocation().distance(entity.getLocation()) < 2) {
                                ticks++;
                                if (ticks >= LOCK_DURATION) {
                                    // 锁定视角
                                    player.setRotation(entity.getLocation().getYaw(), player.getLocation().getPitch());
                                    // 每秒下降1点SAN值
                                    SanValueTool.reduceSanValue(player, 1.0);
                                    // 如果锁定超过5秒，波迪对该玩家产生仇恨
                                     isAngry = true; // 设置波迪为愤怒状态
                                    targetEntity = player; // 设置目标为该玩家
                                }
                            } else {
                                ticks = 0; // 重置计时器
                            }
                        } else {
                            cancel(); // 玩家离开范围，停止检查
                        }
                    }
                }.runTaskTimer(LobotomPlugin.getInstance(), 0, 1); // 每tick检查一次
                break; // 找到波迪后退出循环
            }
        }

        // 检查20米内是否有玩家身上有骨头
        for (Entity entity : player.getWorld().getNearbyEntities(playerLocation, 20, 20, 20)) {
            if (entity instanceof Player) {
                Player targetPlayer = (Player) entity;
                for (ItemStack item : targetPlayer.getInventory().getContents()) {
                    if (item != null && item.getType() == Material.BONE) {
                        // 如果玩家身上有骨头，设置波迪的目标
                        targetEntity = targetPlayer;
                        break;
                    }
                }
            }
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (isBodi(event.getEntity())) {
            // 波迪死亡时掉落0-2个狗毛
            int dropAmount = new Random().nextInt(3); // 随机掉落0到2个
            ItemStack dogFur = DogFur.create(); // 创建狗毛物品
            for (int i = 0; i < dropAmount; i++) {
                event.getDrops().add(dogFur); // 添加到掉落物品中
            }
        }
    }
}
