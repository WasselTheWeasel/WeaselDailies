package wassel.weaseldailies;

import org.bukkit.plugin.java.JavaPlugin;
import wassel.weaseldailies.Commands.DailyRewardCommand;
import wassel.weaseldailies.Commands.ForceDailyRewardCommand;
import wassel.weaseldailies.Commands.ReloadCommand;
import wassel.weaseldailies.Configs.PlayerData;
import wassel.weaseldailies.Controllers.IWeaselDailiesController;
import wassel.weaseldailies.Controllers.WeaselDailiesController;
import wassel.weaseldailies.Helpers.MessageHelper;

public final class WeaselDailies extends JavaPlugin {

    private IWeaselDailiesController weaselDailiesController;
    @Override
    public void onEnable() {
        //Default config
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        reloadConfig();

        //Message helper
        MessageHelper.setup(getConfig());
        MessageHelper.sendConsoleMessage("Setup started");

        //Player data config
        PlayerData.setup();
        PlayerData.get().addDefault("UUID.time", "DateTime");
        PlayerData.get().addDefault("UUID.streak", 0);
        PlayerData.get().options().copyDefaults(true);
        PlayerData.save();
        PlayerData.reload();

        //Main controller
        weaselDailiesController = new WeaselDailiesController(this);

        //Commands
        getCommands();

        MessageHelper.sendConsoleMessage("Setup completed");
    }

    private void getCommands(){
        try {
            getCommand("daily-reward").setExecutor(new DailyRewardCommand(weaselDailiesController));
            getCommand("force-daily-reward").setExecutor(new ForceDailyRewardCommand(weaselDailiesController));
            getCommand("reload").setExecutor(new ReloadCommand(this));
        } catch (Exception e) {
            MessageHelper.sendConsoleMessage("Commands not found");
            throw new RuntimeException(e);
        }
    }

    public void reload(){
        reloadConfig();
        PlayerData.reload();

        //Message helper
        MessageHelper.setup(getConfig());

        //Main controller
        weaselDailiesController = new WeaselDailiesController(this);

        //Commands
        getCommands();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
