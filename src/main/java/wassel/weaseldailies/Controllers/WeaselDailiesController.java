package wassel.weaseldailies.Controllers;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import wassel.weaseldailies.Configs.PlayerData;
import wassel.weaseldailies.Helpers.MessageHelper;
import wassel.weaseldailies.WeaselDailies;

import java.time.LocalDateTime;

public class WeaselDailiesController implements IWeaselDailiesController{
    private final WeaselDailies plugin;
    private final FileConfiguration config;
    private final int lowerThreshold;
    private final int upperThreshold;
    public WeaselDailiesController(WeaselDailies plugin){
        this.plugin = plugin;
        this.config = plugin.getConfig();

        String lowerThresholdString = config.getString("time-thresholds.lower");
        if (lowerThresholdString == null || lowerThresholdString.isEmpty()) throw new RuntimeException("No lower threshold configured.");
        else this.lowerThreshold = Integer.parseInt(lowerThresholdString);

        String upperThresholdString = config.getString("time-thresholds.upper");
        if (upperThresholdString == null || upperThresholdString.isEmpty()) throw new RuntimeException("No upper threshold configured.");
        else this.upperThreshold = Integer.parseInt(upperThresholdString);
    }

    public void DailyReward(Player player){
        boolean giveReward = true;
        LocalDateTime timeNow = LocalDateTime.now();
        int streak = 0;

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

                //Check times
                if (timeNow.isAfter(savedTime.plusHours(lowerThreshold))) {
                    if (timeNow.isBefore(savedTime.plusHours(upperThreshold)))
                        streak++;
                    else{
                        streak = 1;
                        MessageHelper.sendMessageToPlayer(player, config.getString("messages.too-late"));
                    }
                }
                else {
                    giveReward = false;
                    MessageHelper.sendMessageToPlayer(player, config.getString("messages.too-early"));
                }
            }
            else streak = 1;
        }
        else streak = 1;

        if (giveReward){
            SavePlayerDataSection(player, timeNow.toString(), streak);
            GiveReward(player, streak);
        }
    }

    @Override
    public void ForceDailyReward(Player player) {
        LocalDateTime timeNow = LocalDateTime.now();
        int streak = 0;

        ConfigurationSection playerConfigurationSection = PlayerData.get().getConfigurationSection(player.getUniqueId().toString());
        if(playerConfigurationSection != null) {
            String streakString = playerConfigurationSection.getString(".streak");
            if (streakString != null && !streakString.isEmpty()){
                streak = Integer.parseInt(streakString);
                streak++;
            }
            else streak = 1;
        }
        else streak = 1;

        SavePlayerDataSection(player, timeNow.toString(), streak);
        GiveReward(player, streak);
    }

    private void SavePlayerDataSection(Player player, String time, int streak){
        //Fill in missing time and streak
        PlayerData.get().set((player.getUniqueId() + ".time"), time);
        PlayerData.get().set((player.getUniqueId() + ".streak"), streak);
        PlayerData.save();
    }

    private void GiveReward(Player player, int incomingStreak){
        String playerIgn = player.getName();

        int decidedStreak = 0;
        for (String configuredStreakString : config.getConfigurationSection("streak").getKeys(false)){
            int possibleStreak = Integer.parseInt(configuredStreakString);
            if (incomingStreak >= possibleStreak) decidedStreak = possibleStreak;
        }

        if (decidedStreak <= 0){
            MessageHelper.sendConsoleMessage("Something went wrong, the streak can not be 0, please contact the developer");
            MessageHelper.sendMessageToPlayer(player, "Something went wrong, the streak can not be 0, please contact the developer");
            return;
        }

        String command = config.getString("streak." + decidedStreak + ".command");
        command = command.replace("%player%", playerIgn);
        String message = config.getString("streak." + decidedStreak + ".message");
        plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), command);
        MessageHelper.sendMessageToPlayer(player, message);
    }
}
