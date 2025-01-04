package hibikero.lobotomplugin.BackEnd.Listener.Crafting;

import hibikero.lobotomplugin.BackEnd.Crafting.CraftingResult;
import hibikero.lobotomplugin.BackEnd.Crafting.MultiblockCraftingManager;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class MultiblockCraftingListener implements Listener {
    
    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }
        
        Block clickedBlock = event.getClickedBlock();
        if (clickedBlock == null || clickedBlock.getType() != Material.IRON_BARS) {
            return;
        }

        // 检查是否是有效的合成结构
        if (!MultiblockCraftingManager.isValidStructure(clickedBlock)) {
            event.getPlayer().sendMessage("§c无效的合成结构！");
            return;
        }
        
        // 到这里说明是有效的合成结构，取消事件并进行处理
        event.setCancelled(true);
        
        Player player = event.getPlayer();
        CraftingResult result = MultiblockCraftingManager.tryCraft(clickedBlock);
        
        // 只在合成成功时发送消息
        if (result.isSuccess()) {
            player.sendMessage("§a合成成功！");
        }
    }
} 