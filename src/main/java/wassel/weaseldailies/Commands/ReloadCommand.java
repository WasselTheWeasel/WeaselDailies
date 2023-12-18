package wassel.weaseldailies.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import wassel.weaseldailies.WeaselDailies;

public class ReloadCommand implements CommandExecutor {
    private final WeaselDailies plugin;
    public ReloadCommand(WeaselDailies plugin){
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        plugin.reload();
        return true;
    }
}
