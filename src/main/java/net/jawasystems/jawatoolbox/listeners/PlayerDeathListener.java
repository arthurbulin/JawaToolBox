/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jawasystems.jawatoolbox.listeners;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

/** This will protect players who fall into the void and ensure that they keep 
 * their items and XP.
 *
 * @author alexander
 */
public class PlayerDeathListener implements Listener{
    @EventHandler
    public void onPlayerDeath (PlayerDeathEvent event) {
        Location deathLocation = event.getEntity().getLocation();
        
        if (deathLocation.getBlockY() < -3) {
            
            event.setKeepInventory(true);
            event.setKeepLevel(true);
            event.setDeathMessage(event.getEntity().getDisplayName() + ChatColor.GRAY + " has been saved from the void by a Jawa with wings.");
            //event.getEntity().sendMessage(ChatColor.DARK_GRAY + "[JawaCorp apologizes for the hole in existance you fell through. Please keep all your things and levels]" );
        }
        
    }
    
}
