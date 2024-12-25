package hibikero.lobotomplugin.BackEnd.Command;

import hibikero.lobotomplugin.BackEnd.System.San.SanValueTool;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SanCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.isOp()) {
            sender.sendMessage("§c你没有权限使用此命令！");
            return true;
        }

        if (args.length < 3) {
            sender.sendMessage("§c用法: /san <set|add|ded> <玩家名称> <数值>");
            return true;
        }

        Player target = Bukkit.getPlayer(args[1]);
        if (target == null) {
            sender.sendMessage("§c找不到玩家: " + args[1]);
            return true;
        }

        String action = args[0].toLowerCase();
        double value;

        try {
            value = Double.parseDouble(args[2]);
        } catch (NumberFormatException e) {
            sender.sendMessage("§c数值必须是一个有效的数字！");
            return true;
        }

        switch (action) {
            case "set":
                SanValueTool.setSanValue(target, value);
                sender.sendMessage(String.format("§7已将 %s 的SAN值设置为: §f%.2f", target.getName(), value));
                break;
            case "add":
                SanValueTool.increaseSanValue(target, value);
                sender.sendMessage(String.format("§7已将 %s 的SAN值增加: §f%.2f", target.getName(), value));
                break;
            case "ded":
                SanValueTool.reduceSanValue(target, value);
                sender.sendMessage(String.format("§7已将 %s 的SAN值减少: §f%.2f", target.getName(), value));
                break;
            default:
                sender.sendMessage("§c无效的操作！请使用 set、add 或 ded。");
                break;
        }

        return true;
    }
} 