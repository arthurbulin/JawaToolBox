/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jawasystems.jawatoolbox.maintenancemode;

import java.util.UUID;
import net.jawasystems.jawatoolbox.JawaToolBox;
import net.jawasystems.jawatoolbox.handlers.FileHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author alexander
 */
public class MMAdd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command cmnd, String string, String[] args) {
        String usage = "/mmadd <uuid|online player>";
        if (args == null || args.length == 0){
            commandSender.sendMessage(ChatColor.RED + " > You have too few arguments. Usage: " + usage);
            return true; //Short circuit
        } else if (args.length == 1){ //Evaluate and get the UUID
            Player target = Bukkit.getPlayer(args[0]);
            UUID uuid;
            
            if (target == null) {
                try {
                    uuid = UUID.fromString(args[0]);
                } catch (IllegalStateException ex) {
                    commandSender.sendMessage(ChatColor.RED + " > " + args[0] + " is neither an online player, nor a valid UUID.");
                    return true;
                }
            } else {
                uuid = target.getUniqueId();
            }
        
            if (!JawaToolBox.maintenanceList.contains(uuid)) {
                commandSender.sendMessage(ChatColor.GREEN + " > Adding user to the maintenance list and saving the new list.");
                
                JawaToolBox.maintenanceList.add(uuid);
                
                FileHandler.saveMaintenanceList();
            } else {
                commandSender.sendMessage(ChatColor.RED + " > That user's UUID is already in the maintenance list.");
            }
        } else {
            commandSender.sendMessage(ChatColor.RED + " > You have too many arguments. Usage: " + usage);
        }
        
        return true;
    }
    
    
}
