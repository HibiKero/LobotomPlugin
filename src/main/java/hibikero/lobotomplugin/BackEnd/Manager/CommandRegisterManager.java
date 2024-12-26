package hibikero.lobotomplugin.BackEnd.Manager;

import hibikero.lobotomplugin.BackEnd.Command.GetSanCommand;
import hibikero.lobotomplugin.BackEnd.Command.LoBoGetCommand;
import hibikero.lobotomplugin.BackEnd.Command.LoBoSpawnCommand;
import hibikero.lobotomplugin.BackEnd.Command.SanCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class CommandRegisterManager {
    public static void registerCommands(JavaPlugin plugin) {

        GetSanCommand getSanCommand = new GetSanCommand();
        plugin.getCommand("getsan").setExecutor(getSanCommand);
        plugin.getCommand("getsan").setTabCompleter(null);

        LoBoGetCommand loBoGetCommand = new LoBoGetCommand();
        plugin.getCommand("LoBoGet").setExecutor(loBoGetCommand);
        plugin.getCommand("LoBoGet").setTabCompleter(null);

        LoBoSpawnCommand loBoSpawnCommand = new LoBoSpawnCommand();
        plugin.getCommand("LoBoSpawn").setExecutor(loBoSpawnCommand);
        plugin.getCommand("LoBoSpawn").setTabCompleter(null);

        SanCommand sanCommand = new SanCommand();
        plugin.getCommand("san").setExecutor(sanCommand);
        plugin.getCommand("san").setTabCompleter(null);

    }
} 