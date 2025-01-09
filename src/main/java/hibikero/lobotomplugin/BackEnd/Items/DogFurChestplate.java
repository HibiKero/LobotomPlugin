package hibikero.lobotomplugin.BackEnd.Items;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DogFurChestplate {
    public static ItemStack create() {
        ItemStack chestplate = new ItemStack(Material.IRON_CHESTPLATE);
        ItemMeta meta = chestplate.getItemMeta();

        // 设置名称
        meta.setDisplayName("§6波迪的毛皮胸甲");

        // 设置描述
        List<String> lore = new ArrayList<>();
        lore.add("§7由波迪的毛皮制成的胸甲");
        lore.add("§7穿上它，你能感受到波迪的温暖");
        meta.setLore(lore);

        // 设置耐久度
        meta.setUnbreakable(false);
        ((org.bukkit.inventory.meta.Damageable) meta).setDamage(0);

        // 设置防御属性
        AttributeModifier modifier = new AttributeModifier(
            UUID.randomUUID(),
            "generic.armor",
            3.0,
            AttributeModifier.Operation.ADD_NUMBER,
            EquipmentSlot.CHEST
        );
        meta.addAttributeModifier(Attribute.GENERIC_ARMOR, modifier);

        // 设置自定义耐久度
        meta.setCustomModelData(500);

        chestplate.setItemMeta(meta);
        return chestplate;
    }
} 