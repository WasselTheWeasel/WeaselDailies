package wassel.weaseldailies.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import wassel.weaseldailies.WeaselDailies;

import javax.annotation.Nonnull;

public class ReloadCommand implements CommandExecutor {
    private final WeaselDailies plugin;
    public ReloadCommand(WeaselDailies plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@Nonnull CommandSender commandSender, @Nonnull Command command, @Nonnull String s, @Nonnull String[] strings) {
        plugin.reload();
        return true;
    }
}
