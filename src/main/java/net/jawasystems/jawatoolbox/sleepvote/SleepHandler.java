/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jawasystems.jawatoolbox.sleepvote;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.jawasystems.jawatoolbox.JawaToolBox;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

/**
 *
 * @author alexander
 */
public class SleepHandler {
    
    private static HashMap<String,HashMap<UUID,Boolean>> trackVotes = new HashMap();
    private static final double SLEEPPERCENT = JawaToolBox.getConfiguration().getDouble("sleep-percentage", 50.0) / 100.0d;
    private static final HashSet<UUID> SLEEPINGLIST = new HashSet();
    private static final Logger LOGGER = Logger.getLogger("SleepHandler");

    
    public static boolean castVote(Player player, boolean vote){
        if (trackVotes.containsKey(player.getWorld().getName())){
            trackVotes.get(player.getWorld().getName()).put(player.getUniqueId(), vote);
            return true;
        }
        else return false;
    }
    
    public static void startVote(World world){
        HashMap<UUID, Boolean> votes = new HashMap();
        BaseComponent[] baseComp = new ComponentBuilder("> A player has gone to sleep. Vote to make it morning: ").color(ChatColor.GREEN)
            .append("[Yes]").color(ChatColor.YELLOW)
                .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/sleepvote yes"))
                .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Vote Yes").create()))
            .create();
                
        for (Player player : world.getPlayers()){
            votes.put(player.getUniqueId(), Boolean.FALSE);
            player.spigot().sendMessage(baseComp);
        }
        
        trackVotes.put(world.getName(), votes);
        
;
    }
    
    public static void resetVote(World world){
        trackVotes.remove(world.getName());
    }
    
    public static boolean tallyVotes(World world){
        int day = 0;
        for( boolean vote : trackVotes.get(world.getName()).values()){
            if (vote) day++;
        }
        
        if (day > (0.5d * trackVotes.get(world.getName()).size()))
            return true;
        else
            return false;
    }
    
    public static void scheduleExpireVote(World world){
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask (JawaToolBox.getPlugin(), () -> {
            if (world.getTime() < 12000) { //expire
                resetVote(world);
                for (Player player : world.getPlayers()){
                    player.sendMessage(ChatColor.GREEN + "> The dawn has come. The vote has expired.");
                }
            }  else if (world.getTime() >= 12000) { //access vote
                boolean passed = tallyVotes(world);
                if (passed) votePassed(world);
                else scheduleExpireVote(world);
            }
            else { //reschedule
                scheduleExpireVote(world);
            }
        }, 100);
    }
    
    public static void votePassed(World world) {
        for (Player player : world.getPlayers()) {
            player.sendMessage(ChatColor.GREEN + "> The vote has passed. Good morning.");
        }
        world.setTime(0);
        resetVote(world);
    }
    
    public static void startSleepMonitor() {
        
        LOGGER.log(Level.INFO, "Starting Sleep Monitor");
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(JawaToolBox.getPlugin(), () -> {

            int total = Bukkit.getWorld("world").getPlayers().size();
            int sleeping = SLEEPINGLIST.size();
            
            //sleeping = Bukkit.getWorld("world").getPlayers().stream().filter((player) -> (player.isSleeping() || player.isSleepingIgnored())).map((_item) -> 1).reduce(sleeping, Integer::sum);
            SLEEPINGLIST.clear();
            if (!Bukkit.getWorld("world").getPlayers().isEmpty()) {
                for (Player target : Bukkit.getWorld("world").getPlayers()) {
                    if (target.isSleeping()) {
                        SLEEPINGLIST.add(target.getUniqueId());
                    }
                }
            }

            double ratio = (double) SLEEPINGLIST.size() / (double) total;
            
//            LOGGER.log(Level.INFO, "Ratio: {0}", ratio);
//            LOGGER.log(Level.INFO, "sleeping: {0}", sleeping);
//            LOGGER.log(Level.INFO, "total: {0}", total);
//            LOGGER.log(Level.INFO, "sleepinglist: {0}", SLEEPINGLIST.size());
//            LOGGER.log(Level.INFO, "Sleeppercent: {0}", SLEEPPERCENT);

            
            //System.out.println(sleeping + "," + total + "," + JawaToolBox.getConfiguration().getInt("sleep-percentage", 50) + "," + 100.0d);
            if (ratio >= SLEEPPERCENT) {
                SLEEPINGLIST.clear();
                Bukkit.getServer().broadcastMessage(org.bukkit.ChatColor.GREEN + " > **** Good Morning ****");
                Bukkit.getWorld("world").setTime(0);
            } else if (SLEEPINGLIST.size() != sleeping){
                Bukkit.getServer().broadcastMessage(org.bukkit.ChatColor.GOLD + " > " + SLEEPINGLIST.size() + " of " + total + " players sleeping.");
            }

        }, 100, 150);
    }
    
    public static void removeFromSleepingList(UUID uuid){
        if (SLEEPINGLIST.contains(uuid))
        SLEEPINGLIST.remove(uuid);
    }

}
