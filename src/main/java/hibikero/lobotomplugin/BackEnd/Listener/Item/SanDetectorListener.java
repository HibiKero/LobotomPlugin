package hibikero.lobotomplugin.BackEnd.Listener.Item;

import hibikero.lobotomplugin.BackEnd.Items.SanDetector;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

public class SanDetectorListener implements Listener {
    
    @EventHandler
    public void onPlayerInteract(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        
        // 检查是否使用探测器
        if (!SanDetector.isDetector(item)) return;
        
        // 检查目标是否为玩家
        if (!(event.getRightClicked() instanceof Player)) return;
        Player target = (Player) event.getRightClicked();
        
        // 不能检测自己
        if (player.equals(target)) {
            player.sendMessage("§c你无法检测自己的SAN值！");
            return;
        }
        
        // 获取并显示目标的精神状态
        String mentalState = SanDetector.getMentalState(target);
        player.sendMessage(String.format("§7%s的精神状态: %s", target.getName(), mentalState));
        
        event.setCancelled(true);
    }
} 