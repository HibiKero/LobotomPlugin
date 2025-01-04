package hibikero.lobotomplugin.BackEnd.Crafting;

import hibikero.lobotomplugin.BackEnd.Crafting.Recipes.SanDetectorRecipe;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Dispenser;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class MultiblockCraftingManager {
    private static final List<ICraftingRecipe> recipes = new ArrayList<>();

    public static void initRecipes() {
        // 注册配方
        recipes.add(new SanDetectorRecipe());
    }

    public static boolean isValidStructure(Block topBlock) {
        // 检查顶部是否为铁栅栏
        if (topBlock.getType() != Material.IRON_BARS) return false;
        
        // 检查中间是否为发射器
        Block middleBlock = topBlock.getRelative(0, -1, 0);
        if (middleBlock.getType() != Material.DISPENSER) return false;
        
        // 检查底部是否为铁块
        Block bottomBlock = middleBlock.getRelative(0, -1, 0);
        return bottomBlock.getType() == Material.IRON_BLOCK;
    }

    public static CraftingResult tryCraft(Block topBlock) {
        Block middleBlock = topBlock.getRelative(0, -1, 0);
        Dispenser dispenser = (Dispenser) middleBlock.getState();
        Inventory inv = dispenser.getInventory();
        
        // 获取发射器中的物品排列
        ItemStack[] items = inv.getContents();

        // 检查是否匹配任何配方
        for (ICraftingRecipe recipe : recipes) {
            if (recipe.matches(items)) {
                // 获取配方所需的物品数组
                ItemStack[] requiredItems = recipe.getRequiredItems();
                
                // 减少对应格子中的物品数量
                for (int i = 0; i < 9; i++) {
                    if (requiredItems[i] != null && items[i] != null) {
                        int newAmount = items[i].getAmount() - requiredItems[i].getAmount();
                        if (newAmount > 0) {
                            items[i].setAmount(newAmount);
                        } else {
                            items[i] = null;
                        }
                    }
                }
                
                // 更新发射器内容
                inv.setContents(items);
                
                // 在发射器中放入结果物品并返回成功
                inv.addItem(recipe.getResult());
                return new CraftingResult(true, null, null);
            }
        }

        // 如果没有匹配的配方，返回失败
        return new CraftingResult(false, null, null);
    }
} 