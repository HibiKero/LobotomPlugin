package hibikero.lobotomplugin.BackEnd.Command;

import hibikero.lobotomplugin.BackEnd.Entities.Bodi.BodiEntity;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class LoBoSpawnCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player) || !sender.isOp()) {
            sender.sendMessage("§c你没有权限使用此命令！");
            return true;
        }

        if (args.length < 1) {
            sender.sendMessage("§c用法: /LoBoSpawn <生物名称>");
            return true;
        }

        String entityName = args[0].toLowerCase();
        Player player = (Player) sender;

        switch (entityName) {
            case "bodi":
                BodiEntity bodiEntity = new BodiEntity(Bukkit.getPluginManager().getPlugin("LobotomPlugin")); // 传递插件实例
                bodiEntity.spawn(player.getLocation()); // 在玩家位置生成波迪
                player.sendMessage("§7你生成了生物: " + entityName);
                break;
            // 可以在这里添加更多生物的生成逻辑
            default:
                player.sendMessage("§c未知生物名称: " + entityName);
                return true;
        }

        return true;
    }
}