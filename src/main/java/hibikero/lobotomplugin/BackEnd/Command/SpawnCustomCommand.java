package hibikero.lobotomplugin.BackEnd.Command;

import hibikero.lobotomplugin.BackEnd.Entities.CustomEntityManager;
import hibikero.lobotomplugin.BackEnd.Entities.ICustomEntity;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SpawnCustomCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§c该命令只能由玩家执行！");
            return true;
        }

        if (args.length < 1) {
            sender.sendMessage("§c用法: /spawnmob <生物ID>");
            return true;
        }

        String entityId = args[0].toLowerCase();
        if (!CustomEntityManager.hasEntity(entityId)) {
            sender.sendMessage("§c未知的生物类型：" + entityId);
            return true;
        }

        Player player = (Player) sender;
        ICustomEntity customEntity = CustomEntityManager.getEntity(entityId);
        customEntity.spawn(player.getLocation());
        
        player.sendMessage("§a已生成 " + entityId);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            String input = args[0].toLowerCase();
            List<String> completions = new ArrayList<>();
            // 这里需要实现获取所有已注册生物ID的方法
            // 暂时只返回 "bodi"
            if ("bodi".startsWith(input)) {
                completions.add("bodi");
            }
            return completions;
        }
        return new ArrayList<>();
    }
} 