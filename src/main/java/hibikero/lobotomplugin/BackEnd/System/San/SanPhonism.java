package hibikero.lobotomplugin.BackEnd.System.San;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class SanPhonism {
    private static final Map<UUID, Long> insanityCooldowns = new HashMap<>();
    private static final long INSANITY_COOLDOWN_TIME_MIN = 30 * 1000; // 30秒
    private static final long INSANITY_COOLDOWN_TIME_MAX = 60 * 1000; // 1分钟
    public static final String[] SEMMINSANITY_SOUNDS = {
            "block.grass.break", // 破坏草方块的声音
            "entity.item.pickup", // 物品掉落的声音
            "block.note_block.hat", // 音符盒的声音
    };
    public static final String[] INSANITY_SOUNDS = {
            "mob.creeper.say", // 苦力怕的嘶嘶声
            "mob.zombie.ambient", // 僵尸的吼叫声
            "mob.skeleton.walk", // 骷髅的走动声
            "mob.enderman.teleport", // 末影人的传送声
            "mob.spider.walk", // 蜘蛛的爬行声
            "block.grass.break", // 破坏草方块的声音
            "entity.item.pickup", // 物品掉落的声音
            "block.note_block.hat", // 音符盒的声音
    };

    public static void checkHearingVoices(Player player) {
        double currentSan = SanValueTool.getSanValue(player);
        if (currentSan < 30) {
            long currentTime = System.currentTimeMillis();
            if (!insanityCooldowns.containsKey(player.getUniqueId()) ||
                    currentTime - insanityCooldowns.get(player.getUniqueId()) > INSANITY_COOLDOWN_TIME_MIN + (long)(Math.random() * (INSANITY_COOLDOWN_TIME_MAX - INSANITY_COOLDOWN_TIME_MIN))) {
                // 播放幻听声音
                String sound = SEMMINSANITY_SOUNDS[(int) (Math.random() * SEMMINSANITY_SOUNDS.length)];
                player.playSound(player.getLocation(), sound, 1.0f, 1.0f);
                insanityCooldowns.put(player.getUniqueId(), currentTime); // 设置冷却时间
            }
        } else if (currentSan < 15) {
            long currentTime = System.currentTimeMillis();
            if (!insanityCooldowns.containsKey(player.getUniqueId()) ||
                    currentTime - insanityCooldowns.get(player.getUniqueId()) > INSANITY_COOLDOWN_TIME_MIN + (long)(Math.random() * (INSANITY_COOLDOWN_TIME_MAX - INSANITY_COOLDOWN_TIME_MIN))) {
                // 播放幻听声音
                String sound = INSANITY_SOUNDS[(int) (Math.random() * INSANITY_SOUNDS.length)];
                player.playSound(player.getLocation(), sound, 1.0f, 1.0f);
                insanityCooldowns.put(player.getUniqueId(), currentTime); // 设置冷却时间
            }
        }
    }
}
