package wassel.weaseldailies.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import wassel.weaseldailies.Controllers.IWeaselDailiesController;
import wassel.weaseldailies.Helpers.MessageHelper;

import javax.annotation.Nonnull;

public class ForceDailyRewardCommand implements CommandExecutor {
    private final IWeaselDailiesController weaselDailiesController;
    public ForceDailyRewardCommand(IWeaselDailiesController weaselDailiesController){
        this.weaselDailiesController = weaselDailiesController;
    }

    @Override
    public boolean onCommand(@Nonnull CommandSender commandSender, @Nonnull Command command, @Nonnull String s, @Nonnull String[] strings) {
        Player caster = null;
        Player target = null;

        if (commandSender instanceof Player) {
            caster = (Player) commandSender;
        }

        if (strings.length == 0){
            MessageHelper.sendMessageToPlayer(caster, "No target provided");
            return true;
        } else {
            String targetName = strings[0];
            target = Bukkit.getServer().getPlayerExact(targetName);
            if (target == null){
                MessageHelper.sendMessageToPlayer(caster, "Player not found");
                return true;
            }
        }

        weaselDailiesController.ForceDailyReward(target);

        MessageHelper.sendMessageToPlayer(caster, "Force daily reward issued to " + target.getName());

        return true;
    }
}
