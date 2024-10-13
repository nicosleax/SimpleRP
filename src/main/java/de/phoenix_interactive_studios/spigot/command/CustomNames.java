package de.phoenix_interactive_studios.spigot.command;

import de.phoenix_interactive_studios.spigot.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;

public class CustomNames implements CommandExecutor {


    public Main main;


    public CustomNames(Main main){
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if(sender.isOp()) {

            // /setname <yourNewName>, you change your own name
            if (args.length == 1) {

                // changed the name of the player, who initiated the command
                Player pl = (Player) sender;
                String newName = args[0];
                pl.setDisplayName(newName);
                pl.setPlayerListName(newName);

                // new name will be saved in a Map and in the names.json
                main.playerNameMap.put(pl.getUniqueId().toString(), newName);
                main.saveNames();

                // player will get a message about the change of their own name.
                pl.sendMessage(main.getConfig().getString("Prefix") + main.getConfig().getString("Own_Name"));

            }

            // /setname <playerName> <newPlayerName>, you change the name of another player
            else if (args.length == 2) {

                // changed the name of the player, who initiated the command
                String targetPlayerName = args[0];
                String newName = args[1];
                Player targetpl = Bukkit.getPlayer(targetPlayerName);
                targetpl.setDisplayName(newName);
                targetpl.setPlayerListName(newName);

                // new name will be saved in a Map and in the names.json
                main.playerNameMap.put(targetpl.getUniqueId().toString(), newName);
                main.saveNames();

                // player will get a message about the change of the players name.
                sender.sendMessage(main.getConfig().getString("Prefix") + main.getConfig().getString("Change_Message") + ChatColor.GREEN + targetpl + ". " + main.getConfig().getString("New_Name_Message") + ChatColor.AQUA + newName + ".");

                // If the player, who got a name change, is online, the player will get a message about it
                if(targetpl.isOnline()){
                    targetpl.sendMessage(main.getConfig().getString("Prefix") + main.getConfig().getString("Your_New_Name_Message") + ChatColor.AQUA + newName + ".");
                }

            }

        }
        // If the player is no Server Admin to initiate the command, than they will get an Error Message
        else{
            Player pl = (Player) sender;
            pl.sendMessage(main.getConfig().getString("Prefix") + main.getConfig().getString("noNamePermission"));

        }

        return true;
    }
}
