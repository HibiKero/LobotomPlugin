package hibikero.lobotomplugin.BackEnd.Items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class DogFur {
    public static ItemStack create() {
        ItemStack item = new ItemStack(Material.STRING, 1); // 使用线作为狗毛
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("§b超超超可爱的毛"); // 设置物品名称
            List<String> lore = new ArrayList<>();
            lore.add("§7太他妈可爱了"); // 设置物品描述
            meta.setLore(lore);
            item.setItemMeta(meta);
        }
        return item;
    }
} 