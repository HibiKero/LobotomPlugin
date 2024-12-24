package hibikero.lobotomplugin.BackEnd.Crafting.Recipes;

import hibikero.lobotomplugin.BackEnd.Crafting.Abstract3x3CraftingRecipe;
import hibikero.lobotomplugin.BackEnd.Items.SanDetector;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class SanDetectorRecipe extends Abstract3x3CraftingRecipe {
    public SanDetectorRecipe() {
        super(
            "san_detector",
            SanDetector.createDetector(),
            new ItemStack[] {
                null, new ItemStack(Material.IRON_INGOT), null,
                new ItemStack(Material.IRON_INGOT), new ItemStack(Material.ENDER_PEARL), new ItemStack(Material.IRON_INGOT),
                null, new ItemStack(Material.IRON_INGOT), null
            }
        );
    }
} 