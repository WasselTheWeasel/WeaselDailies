package wassel.weaseldailies.Helpers;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;

import static org.bukkit.Bukkit.getServer;

public class MessageHelper {
    private static FileConfiguration config;
    private static String prefix;
    public static void setup(FileConfiguration incomingConfig){
        config = incomingConfig;
        prefix = config.getString("prefix");
    }
    private static String addPrefixAndColor(String message){
        return ChatColor.translateAlternateColorCodes('&', (prefix + message));
    }
    public static void sendConsoleMessage(String message){
        getServer().getConsoleSender().sendMessage(addPrefixAndColor(message));
        //System.out.println(ConsolePrefix + message);
    }
    public static void sendConsoleDebugMessage(String message){
        getServer().getConsoleSender().sendMessage(addPrefixAndColor(ChatColor.RED + "DEBUG " + ChatColor.RESET + message));
    }
    public static void sendMessageToPlayer(@Nullable Player player, String message){
        if (player == null){
            getServer().getConsoleSender().sendMessage(addPrefixAndColor(message));
        }
        else
            player.sendMessage(addPrefixAndColor(message));
    }
}
