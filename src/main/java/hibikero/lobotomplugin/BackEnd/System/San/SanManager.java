package hibikero.lobotomplugin.BackEnd.System.San;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SanManager {
    public static final int DEFAULT_SAN = 200;
    private static final int MAX_SAN = 200;
    private static final Map<UUID, Integer> playerSanValues = new HashMap<>();

    public static int getSanValue(Player player) {
        // 获取玩家的SAN值并向下取整
        return (int) playerSanValues.getOrDefault(player.getUniqueId(), DEFAULT_SAN);
    }

    public static String getMentalState(Player player) {
        int sanValue = getSanValue(player); // 向下取整
        String message;
        String color;

        if (sanValue <= 30) {
            color = "§c"; // 红色
            message = "他疯了，他真的疯了！" + getRandomChaosString(5);
        } else if (sanValue <= 70) {
            color = "§6"; // 橙色
            message = "小心为好，他心中有一个愈加强大的恶魔。" + getRandomChaosString(3);
        } else if (sanValue <= 120) {
            color = "§f"; // 白色
            message = "他起码知道他自己是谁，他自己是什么。";
        } else if (sanValue <= 160) {
            color = "§b"; // 淡蓝色
            message = "嗯，他像你一样是一个正常人。";
        } else {
            color = "§a"; // 绿色
            message = "哦，真是有活力的生命。";
        }

        return color + message;
    }

    private static String getRandomChaosString(int length) {
        String chaosChars = "!@#$%^&*()_+[]{}|;':\",.<>?`~";
        StringBuilder chaosString = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * chaosChars.length());
            chaosString.append(chaosChars.charAt(index));
        }
        return chaosString.toString();
    }
}