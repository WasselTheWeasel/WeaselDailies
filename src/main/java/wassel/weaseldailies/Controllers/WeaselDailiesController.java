package wassel.weaseldailies.Controllers;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import wassel.weaseldailies.Configs.PlayerData;
import wassel.weaseldailies.Helpers.MessageHelper;
import wassel.weaseldailies.WeaselDailies;

import java.time.LocalDateTime;
import java.util.List;

public class WeaselDailiesController implements IWeaselDailiesController{
    private final WeaselDailies plugin;
    private final FileConfiguration config;
    public WeaselDailiesController(WeaselDailies plugin){
        this.plugin = plugin;
        this.config = this.plugin.getConfig();
    }

    public void HandleCommand(Player player){
        boolean giveReward = true;
        LocalDateTime timeNow = LocalDateTime.now();
        int streak = 1;

        int lowerThreshold;
        int upperThreshold;

        //Get the Player Data of the one issuing the command
        ConfigurationSection playerConfigurationSection = PlayerData.get().getConfigurationSection(player.getUniqueId().toString());
        if(playerConfigurationSection != null){
            //Get the time for from the Player Data
            String timeString = playerConfigurationSection.getString(".time");
            if (timeString != null && !timeString.isEmpty()){
                LocalDateTime savedTime = LocalDateTime.parse(timeString);
                //Get the streak for from the Player Data
                String streakString = playerConfigurationSection.getString(".streak");
                if (streakString != null && !streakString.isEmpty()){
                    streak = Integer.parseInt(streakString);
                }
                else //Fill in missing streak
                    PlayerData.get().set((player.getUniqueId() + ".streak"), streak);

            }
            else NewPlayerDataSection(player, timeNow.toString(), streak);
        }
        else NewPlayerDataSection(player, timeNow.toString(), streak);

        if (giveReward)
            GiveReward(player, streak);
    }
    private void NewPlayerDataSection(Player player, String time, int streak){
        //Fill in missing time and streak
        PlayerData.get().set((player.getUniqueId() + ".time"), time);
        PlayerData.get().set((player.getUniqueId() + ".streak"), streak);
    }

    private void GiveReward(Player player, int incomingStreak){
        String playerIgn = player.getName();

        int decidedStreak = 0;

        List<Integer> configuredStreaks = config.getIntegerList("streak");
        for (Integer possibleStreak : configuredStreaks){
            if (incomingStreak >= possibleStreak) decidedStreak = possibleStreak;
        }

        if (decidedStreak <= 0){
            MessageHelper.sendConsoleMessage("Something went wrong, the streak can not be 0, please contact the developer");
            MessageHelper.sendMessageToPlayer(player, "Something went wrong, the streak can not be 0, please contact the developer");
            return;
        }

        String command = config.getString("streak." + decidedStreak + ".command");
        command.replace("%player%", playerIgn);
        String message = config.getString("streak." + decidedStreak + ".message");
        plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), command);
        MessageHelper.sendMessageToPlayer(player, message);
    }
}
