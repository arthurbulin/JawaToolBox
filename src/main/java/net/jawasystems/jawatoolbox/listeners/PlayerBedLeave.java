/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jawasystems.jawatoolbox.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedLeaveEvent;

/**
 *
 * @author alexander
 */
public class PlayerBedLeave implements Listener {

    @EventHandler
    public void PlayerBedLeave(PlayerBedLeaveEvent event) {
        if (event.getPlayer().getWorld().getName().equalsIgnoreCase("world")) {
            //JawaToolBox.sleepList.remove(event.getPlayer().getUniqueId());
            if (Bukkit.getWorld("world").getTime() > 12000) {
                int sleeping = 0;
                int total = Bukkit.getWorld("world").getPlayers().size();

                sleeping = Bukkit.getWorld("world").getPlayers().stream().filter((player) -> (player.isSleeping() || player.isSleepingIgnored())).map((_item) -> 1).reduce(sleeping, Integer::sum);

                Bukkit.getServer().broadcastMessage(ChatColor.GOLD + " > " + sleeping + " of " + total + " players sleeping.");
            }
        }

//        if (event.getPlayer().getWorld().getTime() > 12000) {
//            Bukkit.getServer().broadcastMessage(ChatColor.GOLD + " > " + JawaToolBox.sleepList.size() + " of " + event.getPlayer().getWorld().getPlayers().size() + " users sleeping.");
//        }
    }
}
