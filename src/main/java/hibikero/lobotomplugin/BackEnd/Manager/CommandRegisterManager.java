package hibikero.lobotomplugin.BackEnd.Manager;

import hibikero.lobotomplugin.BackEnd.Command.SpawnCustomCommand;
import hibikero.lobotomplugin.BackEnd.Command.GetSanCommand;
import hibikero.lobotomplugin.LobotomPlugin;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;

public class CommandRegisterManager {
    public static void registerCommands(LobotomPlugin plugin) {
        // 注册命令
        registerCommand(plugin, "spawnmob", new SpawnCustomCommand());
        registerCommand(plugin, "getsan", new GetSanCommand());
    }

    private static void registerCommand(LobotomPlugin plugin, String commandName, CommandExecutor executor) {
        plugin.getCommand(commandName).setExecutor(executor);
        if (executor instanceof TabCompleter) {
            plugin.getCommand(commandName).setTabCompleter((TabCompleter) executor);
        }
    }
} 