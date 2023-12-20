/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jawasystems.jawatoolbox.sleepvote;

import net.jawasystems.jawatoolbox.JawaToolBox;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;

/**
 *
 * @author alexander
 */
public class PlayerBedEnter implements Listener {

    @EventHandler
    public void PlayerBedEnter(PlayerBedEnterEvent event) {

        if (event.getBedEnterResult().equals(PlayerBedEnterEvent.BedEnterResult.OK) && event.getPlayer().getWorld().getName().equalsIgnoreCase("world")) {
            double sleepPercentage = JawaToolBox.getConfiguration().getDouble("sleep-percentage", 50.0) / 100.0d;
            //SleepHandler.startVote(event.getPlayer().getWorld());
            //Check once they are asleep
            Bukkit.getServer().getScheduler().runTaskLater(JawaToolBox.getPlugin(), () -> {

                int sleeping = 0;
                int total = Bukkit.getWorld("world").getPlayers().size();

                //sleeping = Bukkit.getWorld("world").getPlayers().stream().filter((player) -> (player.isSleeping() || player.isSleepingIgnored())).map((_item) -> 1).reduce(sleeping, Integer::sum);

                for (Player target : Bukkit.getWorld("world").getPlayers()){
                    if (target.isSleeping() || target.isSleepingIgnored()){
                        sleeping++;
                    }
                }
                
                double ratio = (double) sleeping / (double) total;
                
                //System.out.println(sleeping + "," + total + "," + JawaToolBox.getConfiguration().getInt("sleep-percentage", 50) + "," + 100.0d);
                if ( ratio > sleepPercentage) {
                    Bukkit.getServer().broadcastMessage(org.bukkit.ChatColor.GREEN + " > **** Good Morning ****");
                    Bukkit.getWorld("world").setTime(0);
                } else {

                    Bukkit.getServer().broadcastMessage(ChatColor.GOLD + " > " + sleeping + " of " + total + " players sleeping.");
                }

            }, 100);
//            JawaToolBox.sleepList.add(event.getPlayer().getUniqueId());
//            
//            //Object[] players = Bukkit.getServer().getOnlinePlayers().toArray();
//            double total = Bukkit.getWorld("world").getPlayers().size();
//            double sleeping = JawaToolBox.sleepList.size();
//
//            Bukkit.getServer().broadcastMessage(ChatColor.GOLD + " > " + JawaToolBox.sleepList.size() + " of " + event.getPlayer().getWorld().getPlayers().size() + " users sleeping.");
//            if ((sleeping / total) >= 0.5d) {
//                Bukkit.getServer().broadcastMessage(ChatColor.GREEN + " > **** Good Morning ****");
//                Bukkit.getWorld("world").setTime(0);
//                
//            }
        }

    }

}
