package hibikero.lobotomplugin.BackEnd.Crafting.Recipes;

import hibikero.lobotomplugin.BackEnd.Crafting.Abstract3x3CraftingRecipe;
import hibikero.lobotomplugin.BackEnd.Items.DogFur;
import hibikero.lobotomplugin.BackEnd.Items.DogFurChestplate;
import org.bukkit.inventory.ItemStack;

public class DogFurChestplateRecipe extends Abstract3x3CraftingRecipe {
    public DogFurChestplateRecipe() {
        super(
            "dog_fur_chestplate",
            DogFurChestplate.create(),
            new ItemStack[] {
                DogFur.create(), null, DogFur.create(),
                DogFur.create(), DogFur.create(), DogFur.create(),
                DogFur.create(), DogFur.create(), DogFur.create()
            }
        );
    }
} 