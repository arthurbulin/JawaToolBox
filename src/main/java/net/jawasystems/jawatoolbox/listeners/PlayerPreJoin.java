/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jawasystems.jawatoolbox.listeners;

import net.jawasystems.jawatoolbox.JawaToolBox;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

/**
 *
 * @author alexander
 */
public class PlayerPreJoin implements Listener{
    
    @EventHandler
    public static void onPlayerJoin(AsyncPlayerPreLoginEvent event) {
        if (JawaToolBox.maintenanceMode && !JawaToolBox.maintenanceList.contains(event.getUniqueId())) {
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, "This server is currently in maintenance mode. Only authorized personel may join.");
        }
    }
    
}
