package wassel.weaseldailies.Configs;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class PlayerData {

    private static File file;
    private static FileConfiguration config;

    //Finds or generates PlayerData config file
    public static void setup(){
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("WeaselDailies").getDataFolder(), "PlayerData");
        if (!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("Unable to create file");
                throw new RuntimeException(e);
            }
        }
        config = YamlConfiguration.loadConfiguration(file);
    }

    public static FileConfiguration get(){
        return config;
    }

    public static void save(){
        try {
            config.save(file);
        } catch (IOException e) {
            System.out.println("Unable to save file");
            throw new RuntimeException(e);
        }
    }

    public static void reload(){
        config = YamlConfiguration.loadConfiguration(file);
    }
}
