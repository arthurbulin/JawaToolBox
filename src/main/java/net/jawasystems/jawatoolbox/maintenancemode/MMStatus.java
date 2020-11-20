/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jawasystems.jawatoolbox.maintenancemode;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 *
 * @author alexander
 */
public class MMStatus implements CommandExecutor{

    @Override
    public boolean onCommand(CommandSender commandSender, Command cmnd, String string, String[] args) {
        String stateString;
        
        if (MaintenanceModeHandler.getMMStatus()) stateString = ChatColor.DARK_GREEN + "Online";
        else stateString = ChatColor.RED + "Offline";
        
        commandSender.sendMessage(ChatColor.GREEN + " > Maintenance mode is currently: " + stateString + ChatColor.GREEN + " with level " + MaintenanceModeHandler.getMMLevel());
        
        return true;
    }
    
}
