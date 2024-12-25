package hibikero.lobotomplugin.BackEnd.Command;

import hibikero.lobotomplugin.BackEnd.Items.SanDetector;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class LoBoGetCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player) || !sender.isOp()) {
            sender.sendMessage("§c你没有权限使用此命令！");
            return true;
        }

        Player player = (Player) sender;

        if (args.length < 1) {
            player.sendMessage("§c用法: /LoBoGet <物品名称>");
            return true;
        }

        String itemName = args[0].toLowerCase();
        ItemStack item;

        switch (itemName) {
            case "san_detector":
                item = SanDetector.createDetector();
                break;
            // 可以在这里添加更多物品的获取逻辑
            default:
                player.sendMessage("§c未知物品名称: " + itemName);
                return true;
        }

        player.getInventory().addItem(item);
        player.sendMessage("§7你获得了物品: " + itemName);
        return true;
    }
} 