package hibikero.lobotomplugin.BackEnd.Listener.Crafting;

import hibikero.lobotomplugin.BackEnd.Crafting.CraftingResult;
import hibikero.lobotomplugin.BackEnd.Crafting.MultiblockCraftingManager;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class MultiblockCraftingListener implements Listener {
    
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        
        Block clickedBlock = event.getClickedBlock();
        if (clickedBlock == null || clickedBlock.getType() != Material.IRON_BARS) return;
        
        Player player = event.getPlayer();
        
        // 尝试合成
        CraftingResult result = MultiblockCraftingManager.tryCraft(clickedBlock);
        
        // 发送消息给玩家
        player.sendMessage(result.getMessage());
        
        // 如果合成成功，给予物品
        if (result.isSuccess()) {
            player.getWorld().dropItem(player.getLocation(), result.getResult());
        }
        
        event.setCancelled(true);
    }
} 