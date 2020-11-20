/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jawasystems.jawatoolbox.listeners;

import net.jawasystems.jawatoolbox.JawaToolBox;
import net.jawasystems.jawatoolbox.handlers.MOTDHandler;
import net.jawasystems.jawatoolbox.maintenancemode.MaintenanceModeHandler;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 *
 * @author alexander
 */
public class PlayerJoin implements Listener{
    
    @EventHandler
    public static void onPlayerJoin(PlayerJoinEvent event) {
        if (MaintenanceModeHandler.getMMStatus() && event.getPlayer().hasPermission("jawatoolbox.mm.toggle")){
            event.getPlayer().sendMessage(ChatColor.GREEN + " > **** Maintenance Mode is ONLINE with level: " + MaintenanceModeHandler.getMMLevel() + " ****");
        }
        
        if (MaintenanceModeHandler.getMMStatus()) {
            MaintenanceModeHandler.evaluateUser(event.getPlayer());
        }
        
        if (event.getPlayer().hasPermission("jawatoolbox.motd")){
            MOTDHandler.sendNormalMOTD(event.getPlayer());
        }
    }
    
}
