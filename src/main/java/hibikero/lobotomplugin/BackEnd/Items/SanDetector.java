package hibikero.lobotomplugin.BackEnd.Items;

import hibikero.lobotomplugin.LobotomPlugin;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;

public class SanDetector {
    private static final NamespacedKey DETECTOR_KEY = new NamespacedKey(LobotomPlugin.getInstance(), "san_detector");

    public static ItemStack createDetector() {
        ItemStack detector = new ItemStack(Material.COMPASS);
        ItemMeta meta = detector.getItemMeta();
        
        // 设置物品名称和描述
        meta.setDisplayName("§6SAN值探测器");
        meta.setLore(Arrays.asList(
            "§7右键点击玩家以查看其SAN值",
            "§c无法查看自己的SAN值"
        ));
        
        // 添加自定义NBT标签
        meta.getPersistentDataContainer().set(DETECTOR_KEY, PersistentDataType.BYTE, (byte)1);
        
        detector.setItemMeta(meta);
        return detector;
    }

    public static boolean isDetector(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return false;
        return item.getItemMeta().getPersistentDataContainer().has(DETECTOR_KEY, PersistentDataType.BYTE);
    }
} 