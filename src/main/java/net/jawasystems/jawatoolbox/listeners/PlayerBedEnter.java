/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jawasystems.jawatoolbox.listeners;

import net.jawasystems.jawatoolbox.JawaToolBox;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;

/**
 *
 * @author alexander
 */
public class PlayerBedEnter implements Listener{
    
    @EventHandler
    public void PlayerBedEnter(PlayerBedEnterEvent event) {
        
        if (event.getBedEnterResult().equals(PlayerBedEnterEvent.BedEnterResult.OK) && event.getPlayer().getWorld().getName().equalsIgnoreCase("world")) {
            JawaToolBox.sleepList.add(event.getPlayer().getUniqueId());
            
            //Object[] players = Bukkit.getServer().getOnlinePlayers().toArray();
            double total = Bukkit.getWorld("world").getPlayers().size();
            double sleeping = JawaToolBox.sleepList.size();

            Bukkit.getServer().broadcastMessage(ChatColor.GOLD + " > " + JawaToolBox.sleepList.size() + " of " + event.getPlayer().getWorld().getPlayers().size() + " users sleeping.");
            if ((sleeping / total) >= 0.5d) {
                Bukkit.getServer().broadcastMessage(ChatColor.GREEN + " > **** Good Morning ****");
                Bukkit.getWorld("world").setTime(0);
            }
        }
        
    }
    
}
