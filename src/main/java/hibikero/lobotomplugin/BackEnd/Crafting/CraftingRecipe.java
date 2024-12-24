package hibikero.lobotomplugin.BackEnd.Crafting;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class CraftingRecipe {
    private final List<ItemStack> ingredients;
    private final ItemStack result;

    public CraftingRecipe(ItemStack result, ItemStack... ingredients) {
        this.ingredients = Arrays.asList(ingredients);
        this.result = result;
    }

    public boolean matches(List<ItemStack> items) {
        if (items.size() != ingredients.size()) return false;
        
        // 创建配方材料的副本，用于检查
        List<ItemStack> remainingIngredients = new ArrayList<>(ingredients);
        
        // 检查每个提供的物品是否匹配配方中的任意材料
        for (ItemStack item : items) {
            boolean found = false;
            for (ItemStack ingredient : new ArrayList<>(remainingIngredients)) {
                if (itemMatches(item, ingredient)) {
                    remainingIngredients.remove(ingredient);
                    found = true;
                    break;
                }
            }
            if (!found) return false;
        }
        
        return remainingIngredients.isEmpty();
    }

    private boolean itemMatches(ItemStack item1, ItemStack item2) {
        return item1 != null && item2 != null && 
               item1.getType() == item2.getType() && 
               item1.getAmount() >= item2.getAmount();
    }

    public ItemStack getResult() {
        return result.clone();
    }
} 