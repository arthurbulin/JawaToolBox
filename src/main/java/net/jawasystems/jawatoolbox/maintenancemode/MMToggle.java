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
public class MMToggle implements CommandExecutor{

    //normal (staff only), list (only in uuid list), ranked (only ranked players), 0,1,2,3,etc (users and above with permission jawatoolbox.mm.#)
    
    @Override
    public boolean onCommand(CommandSender commandSender, Command cmnd, String string, String[] args) {
        String stateString;
        
        if (!MaintenanceModeHandler.getMMStatus()) stateString = ChatColor.DARK_GREEN + "Online";
        else stateString = ChatColor.RED + "Offline";
        
        if (args.length >= 1){
            if (args[0].matches("no?r?m?a?l?")) {
                MaintenanceModeHandler.setMMLevel("normal");
            } else if (args[0].matches("li?s?t?")) {
                MaintenanceModeHandler.setMMLevel("list");
            } else if (args[0].matches("ra?n?k?e?d?")){
                MaintenanceModeHandler.setMMLevel("ranked");
            } else if (args[0].matches("[0-9]+")){
                MaintenanceModeHandler.setMMLevel(args[0]);
            } else {
                commandSender.sendMessage(ChatColor.RED + "> Error: That is not an understood option." + ChatColor.AQUA + " Please specify normal, list, ranked, or a #");
                return true;
            }
            MaintenanceModeHandler.setMMStatus(true);
        } else {
            commandSender.sendMessage(ChatColor.GREEN + " > Toggling maintenance mode " + stateString + ChatColor.GREEN + " for this session. A restart or toggle will set this back to the configured default.");
            MaintenanceModeHandler.setMMStatus(!MaintenanceModeHandler.getMMStatus());
        }
        return true;
    }
    
}
