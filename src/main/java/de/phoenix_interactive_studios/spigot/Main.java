package de.phoenix_interactive_studios.spigot;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import de.phoenix_interactive_studios.spigot.command.CustomNames;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class Main extends JavaPlugin{

    // Create a .json File to store all custom names in it.
    public File names = new File("names.json");

    //Create a Map to save temporary the new name of a player
    public Map<String, String> playerNameMap = new HashMap<>();

    // Add Gson Library to handle json
    public Gson gson = new Gson();

    public void onEnable(){

        getConfig().options().copyDefaults();
        saveDefaultConfig();

        getCommand("setname").setExecutor(new CustomNames(this));
        loadNames();
    }

    @Override
    public void onDisable() {
        saveNames();
    }


    public void loadNames(){

        try(FileReader nameReader = new FileReader(names)) {
            Type mapType = new TypeToken<Map<String, String>>() {}.getType();
            Map<String, String> loadedMap = gson.fromJson(nameReader, mapType);
            if(loadedMap != null){
                playerNameMap.putAll(loadedMap);
            }
        }

        catch (IOException e) {
            e.printStackTrace();
            System.out.println("Theres a problem about the loading of the custom names!");
        }

    }

    public void saveNames(){

        if (!getDataFolder().exists()) {
                getDataFolder().mkdir();
            }

        try (FileWriter namewriter = new FileWriter(names)){
                gson.toJson(playerNameMap, namewriter);
            }
        catch (IOException e){
                e.printStackTrace();
                System.out.println("Theres a problem to save the names!");
            }
    }

}
