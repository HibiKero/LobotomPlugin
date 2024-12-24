package hibikero.lobotomplugin.BackEnd.Crafting;

import org.bukkit.inventory.ItemStack;

public class CraftingResult {
    private final boolean success;
    private final ItemStack result;
    private final String message;

    public CraftingResult(boolean success, ItemStack result, String message) {
        this.success = success;
        this.result = result;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public ItemStack getResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }
} 