package hibikero.lobotomplugin.BackEnd.System.San;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SanAlert {
    private static final Map<UUID, Long> insanityCooldowns = new HashMap<>();
    private static final long INSANITY_COOLDOWN_TIME_MIN = 60 * 1000; // 1分钟
    private static final long INSANITY_COOLDOWN_TIME_MAX = 180 * 1000; // 3分钟

    public static void checkSanAlert(Player player) {
        double currentSan = SanValueTool.getSanValue(player);
        if (currentSan < 20) {
            long currentTime = System.currentTimeMillis();
            if (!insanityCooldowns.containsKey(player.getUniqueId()) || 
                currentTime - insanityCooldowns.get(player.getUniqueId()) > INSANITY_COOLDOWN_TIME_MAX) {
                player.sendMessage("§c你感觉你愈发疯狂了！"); // 红色字体提示
                player.playSound(player.getLocation(), "ambient.cave", 1.0f, 1.0f); // 播放声音
                insanityCooldowns.put(player.getUniqueId(), currentTime); // 设置冷却时间
            }
        }
    }
} 