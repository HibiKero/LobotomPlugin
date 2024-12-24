package hibikero.lobotomplugin.BackEnd.Command;

import hibikero.lobotomplugin.BackEnd.Items.SanDetector;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GetDetectorCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§c该命令只能由玩家执行！");
            return true;
        }
        
        Player player = (Player) sender;
        player.getInventory().addItem(SanDetector.createDetector());
        player.sendMessage("§a你获得了一个SAN值探测器！");
        
        return true;
    }
} 