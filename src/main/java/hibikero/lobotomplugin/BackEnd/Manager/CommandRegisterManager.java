package hibikero.lobotomplugin.BackEnd.Manager;

import hibikero.lobotomplugin.BackEnd.Command.GetSanCommand;
import hibikero.lobotomplugin.BackEnd.Command.LoBoGetCommand;
import hibikero.lobotomplugin.BackEnd.Command.SanCommand;
import hibikero.lobotomplugin.BackEnd.Command.SpawnCustomCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class CommandRegisterManager {
    public static void registerCommands(JavaPlugin plugin) {

        GetSanCommand getSanCommand = new GetSanCommand();
        plugin.getCommand("getsan").setExecutor(getSanCommand);
        plugin.getCommand("getsan").setTabCompleter(null);

        LoBoGetCommand loBoGetCommand = new LoBoGetCommand();
        plugin.getCommand("LoBoGet").setExecutor(loBoGetCommand);
        plugin.getCommand("LoBoGet").setTabCompleter(null);

        SanCommand sanCommand = new SanCommand();
        plugin.getCommand("san").setExecutor(sanCommand);
        plugin.getCommand("san").setTabCompleter(null);

        SpawnCustomCommand spawnCustomCommand = new SpawnCustomCommand();
        plugin.getCommand("spawnmob").setExecutor(spawnCustomCommand);
        plugin.getCommand("spawnmob").setTabCompleter(null);

    }
} 