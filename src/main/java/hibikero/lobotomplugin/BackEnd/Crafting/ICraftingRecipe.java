package hibikero.lobotomplugin.BackEnd.Crafting;

import org.bukkit.inventory.ItemStack;

public interface ICraftingRecipe {
    // 检查合成表是否匹配
    boolean matches(ItemStack[] items);
    
    // 获取合成结果
    ItemStack getResult();
    
    // 获取配方ID
    String getRecipeId();
} 