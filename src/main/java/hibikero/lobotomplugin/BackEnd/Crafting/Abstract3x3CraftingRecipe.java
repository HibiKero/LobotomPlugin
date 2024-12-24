package hibikero.lobotomplugin.BackEnd.Crafting;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public abstract class Abstract3x3CraftingRecipe implements ICraftingRecipe {
    protected final ItemStack[] pattern;
    protected final ItemStack result;
    protected final String recipeId;

    protected Abstract3x3CraftingRecipe(String recipeId, ItemStack result, ItemStack[] pattern) {
        if (pattern.length != 9) {
            throw new IllegalArgumentException("Pattern must contain exactly 9 items!");
        }
        this.pattern = pattern;
        this.result = result;
        this.recipeId = recipeId;
    }

    @Override
    public boolean matches(ItemStack[] items) {
        if (items.length != 9) return false;
        
        for (int i = 0; i < 9; i++) {
            if (!itemMatches(items[i], pattern[i])) {
                return false;
            }
        }
        return true;
    }

    protected boolean itemMatches(ItemStack item1, ItemStack item2) {
        // 如果配方中这个位置是空的
        if (item2 == null || item2.getType() == Material.AIR) {
            return item1 == null || item1.getType() == Material.AIR;
        }
        // 如果提供的物品是空的但配方不是空的
        if (item1 == null || item1.getType() == Material.AIR) {
            return false;
        }
        // 检查物品类型和数量
        return item1.getType() == item2.getType() && 
               item1.getAmount() >= item2.getAmount();
    }

    @Override
    public ItemStack getResult() {
        return result.clone();
    }

    @Override
    public String getRecipeId() {
        return recipeId;
    }
} 