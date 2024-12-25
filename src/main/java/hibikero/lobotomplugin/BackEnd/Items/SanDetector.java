package hibikero.lobotomplugin.BackEnd.Items;

import hibikero.lobotomplugin.BackEnd.System.San.SanValueTool;
import hibikero.lobotomplugin.LobotomPlugin;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.entity.Player;

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

    public static String getMentalState(Player player) {
        double sanValue = SanValueTool.getSanValue(player);
        String message;
        String color;

        if (sanValue <= 30) {
            color = "§c"; // 红色
            message = "你感觉你愈发疯狂了！";
        } else if (sanValue <= 70) {
            color = "§6"; // 橙色
            message = "小心为好，他心中有一个愈加强大的恶魔。";
        } else if (sanValue <= 120) {
            color = "§f"; // 白色
            message = "你起码知道你自己是谁，你自己是什么。";
        } else if (sanValue <= 160) {
            color = "§b"; // 淡蓝色
            message = "嗯，你像一个正常人。";
        } else {
            color = "§a"; // 绿色
            message = "哦，真是有活力的生命。";
        }

        return color + message;
    }
} 