/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jawasystems.jawatoolbox.maintenancemode;

import net.jawasystems.jawatoolbox.JawaToolBox;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 *
 * @author alexander
 */
public class MMToggle implements CommandExecutor{

    @Override
    public boolean onCommand(CommandSender commandSender, Command cmnd, String string, String[] args) {
        String stateString;
        
        if (!JawaToolBox.maintenanceMode) stateString = ChatColor.DARK_GREEN + "Online";
        else stateString = ChatColor.RED + "Offline";
        
        commandSender.sendMessage(ChatColor.GREEN + " > Toggling maintenance mode " + stateString + ChatColor.GREEN + " for this session. A restart or toggle will set this back to the configured default.");
        
        JawaToolBox.maintenanceMode = !JawaToolBox.maintenanceMode;
        return true;
    }
    
}
