package wassel.weaseldailies.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import wassel.weaseldailies.Controllers.IWeaselDailiesController;
import wassel.weaseldailies.Helpers.MessageHelper;

import javax.annotation.Nonnull;

public class DailyRewardCommand implements CommandExecutor {
    private final IWeaselDailiesController weaselDailiesController;
    public DailyRewardCommand(IWeaselDailiesController weaselDailiesController){
        this.weaselDailiesController = weaselDailiesController;
    }

    @Override
    public boolean onCommand(@Nonnull CommandSender commandSender, @Nonnull Command command, @Nonnull String s, @Nonnull String[] strings) {
        if (!(commandSender instanceof Player)){
            //Can't be used from console
            MessageHelper.sendConsoleMessage("This command can not be used from the console");
            return true;
        }
        Player player = (Player) commandSender;

        weaselDailiesController.DailyReward(player);

        return true;
    }
}
