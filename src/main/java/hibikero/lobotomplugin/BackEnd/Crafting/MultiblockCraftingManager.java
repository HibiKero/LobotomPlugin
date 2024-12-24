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
        if (!isValidStructure(topBlock)) {
            return new CraftingResult(false, null, "§c无效的合成结构！");
        }

        Block middleBlock = topBlock.getRelative(0, -1, 0);
        Dispenser dispenser = (Dispenser) middleBlock.getState();
        Inventory inv = dispenser.getInventory();
        
        // 获取发射器中的物品排列
        ItemStack[] items = inv.getContents();

        // 检查是否匹配任何配方
        for (ICraftingRecipe recipe : recipes) {
            if (recipe.matches(items)) {
                // 清空发射器
                inv.clear();
                // 放入结果物品
                inv.addItem(recipe.getResult());
                return new CraftingResult(true, recipe.getResult(), "§a合成成功！");
            }
        }

        return new CraftingResult(false, null, "§c材料不正确或摆放位置错误！");
    }
} 