package wassel.weaseldailies.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import wassel.weaseldailies.Helpers.MessageHelper;
import wassel.weaseldailies.WeaselDailies;

import javax.annotation.Nonnull;

public class ReloadCommand implements CommandExecutor {
    private final WeaselDailies plugin;
    public ReloadCommand(WeaselDailies plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@Nonnull CommandSender commandSender, @Nonnull Command command, @Nonnull String s, @Nonnull String[] strings) {
        Player caster = null;
        if (commandSender instanceof Player) {
            caster = (Player) commandSender;
        }
        MessageHelper.sendMessageToPlayer(caster, "Reload started");
        plugin.reload();
        MessageHelper.sendMessageToPlayer(caster, "Reload finished");
        return true;
    }
}
