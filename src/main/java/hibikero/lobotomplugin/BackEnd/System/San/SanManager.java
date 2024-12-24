package hibikero.lobotomplugin.BackEnd.System.San;

import hibikero.lobotomplugin.BackEnd.Manager.SanDataManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SanManager {
    public static final int DEFAULT_SAN = 200;
    private static final int MAX_SAN = 200;
    private static final Map<UUID, Double> playerSanValues = new HashMap<>();

    public static void initializePlayer(Player player) {
        setSanValue(player, DEFAULT_SAN);
    }

    public static double getSanValue(Player player) {
        return playerSanValues.getOrDefault(player.getUniqueId(), (double) DEFAULT_SAN);
    }

    public static void removePlayer(Player player) {
        playerSanValues.remove(player.getUniqueId());
    }

    public static String getMentalState(Player player) {
        double sanValue = getSanValue(player);
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

    public static void startSanDecayTask(Player player) {
        new BukkitRunnable() {
            @Override
            public void run() {
                // 检查是否是夜晚
                if (Bukkit.getWorlds().get(0).getTime() >= 13000 && Bukkit.getWorlds().get(0).getTime() <= 23000) {
                    Location playerLocation = player.getLocation();
                    if (playerLocation.getBlock().getLightLevel() <= 8) {
                        reduceSanValue(player, 0.1); // 每秒减少0.1点SAN值
                    }
                }

                // 处理饥饿值对SAN值的影响
                int hungerLevel = player.getFoodLevel();
                if (hungerLevel > 19) {
                    // 每秒恢复0.4 SAN值
                    increaseSanValue(player, 0.15); // 恢复0.15点SAN值
                } else if (hungerLevel < 10) {
                    // 每秒减少0.1 SAN值
                    reduceSanValue(player, 0.1);
                }
                if (hungerLevel < 6) {
                    // 每秒减少0.5 SAN值
                    reduceSanValue(player, 0.5);
                }
            }
        }.runTaskTimer(Bukkit.getPluginManager().getPlugin("LobotomPlugin"), 0, 20); // 每20个tick（1秒）执行一次
    }

    public static void reduceSanValue(Player player, double amount) {
        double currentSan = getSanValue(player);
        setSanValue(player, Math.max(currentSan - amount, 0));
    }

    public static void setSanValue(Player player, double value) {
        double sanValue = Math.min(Math.max(value, 0), MAX_SAN);
        playerSanValues.put(player.getUniqueId(), sanValue);
        SanDataManager.saveSanValue(player.getUniqueId(), (int) sanValue); // 保存时向下取整
    }

    public static void increaseSanValue(Player player, double amount) {
        double currentSan = getSanValue(player);
        setSanValue(player, Math.min(currentSan + amount, MAX_SAN)); // 确保SAN值不超过上限
    }
}