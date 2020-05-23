/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jawasystems.jawatoolbox;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;

/**
 *
 * @author Jawamaster (Arthur Bulin)
 */
public class JawaToolBoxAbout implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        
        ComponentBuilder message; 
        FileConfiguration changeLog = JawaToolBox.getChangeLog();
        PluginDescriptionFile description = JawaToolBox.getPlugin().getDescription();
        if (args == null || args.length == 0){
            String latest = changeLog.getString("latest");
            commandSender.sendMessage(ChatColor.BLUE + "> This server is running " + ChatColor.GOLD + description.getFullName()
                    //+ " version " + config.get("version") 
                    + ChatColor.BLUE + " with build date " + ChatColor.DARK_GREEN + changeLog.getString("build-date"));
            commandSender.sendMessage(ChatColor.BLUE + " > Coded by " + description.getDescription());
            BaseComponent[] authorInfo = new ComponentBuilder(" > ").color(ChatColor.BLUE)
                    .append(ChatColor.GOLD + description.getAuthors().get(0)).color(ChatColor.GOLD)
                    .append(" ")
                    .append(description.getWebsite()).color(ChatColor.WHITE)
                        .event(new ClickEvent(ClickEvent.Action.OPEN_URL,description.getWebsite()))
                        .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Open Link").create()))
                    .create();
            
            commandSender.spigot().sendMessage(authorInfo);
            //commandSender.sendMessage(ChatColor.BLUE + " > Coded by " + ChatColor.GOLD + description.getAuthors().get(0) + " " + ChatColor.BLUE  + description.getWebsite());
            
            if (changeLog.contains(latest)) {
                message = new ComponentBuilder("> This is the latest version changelog: ").color(ChatColor.BLUE);
                BaseComponent[] baseCompo = message.create();
                commandSender.spigot().sendMessage(baseCompo);
                for (String item : changeLog.getStringList(latest)){
                    BaseComponent[] lineItem = new ComponentBuilder(" > ").color(ChatColor.GREEN)
                            .append(item).color(ChatColor.BLUE)
                            .create();
                    commandSender.spigot().sendMessage(lineItem);
                }
            } else {
                message = new ComponentBuilder("> The change log is malformed and the latest version cannot be assertained").color(ChatColor.RED);
                BaseComponent[] baseCompo = message.create();
                commandSender.spigot().sendMessage(baseCompo);
            }
        } else {
            if (changeLog.contains(args[0])) {
                message = new ComponentBuilder("> Change log for version " + args[0]).color(ChatColor.BLUE);
                BaseComponent[] baseCompo = message.create();
                commandSender.spigot().sendMessage(baseCompo);
                for (String item : changeLog.getStringList(args[0])) {
                    BaseComponent[] lineItem = new ComponentBuilder(" > ").color(ChatColor.GREEN)
                            .append(item).color(ChatColor.BLUE)
                            .create();
                    commandSender.spigot().sendMessage(lineItem);
                }
            } else {
                message = new ComponentBuilder("> That version is not in the ChangeLog.").color(ChatColor.RED);
                BaseComponent[] baseCompo = message.create();
                commandSender.spigot().sendMessage(baseCompo);
            }
        }
        return true;
    }
    
}
