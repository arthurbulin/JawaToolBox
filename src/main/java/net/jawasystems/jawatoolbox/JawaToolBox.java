/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jawasystems.jawatoolbox;

import java.util.HashSet;
import java.util.UUID;
import net.jawasystems.jawatoolbox.handlers.FileHandler;
import net.jawasystems.jawatoolbox.listeners.PlayerJoin;
import net.jawasystems.jawatoolbox.listeners.PlayerPreJoin;
import net.jawasystems.jawatoolbox.maintenancemode.MMAdd;
import net.jawasystems.jawatoolbox.maintenancemode.MMRemove;
import net.jawasystems.jawatoolbox.maintenancemode.MMStatus;
import net.jawasystems.jawatoolbox.maintenancemode.MMToggle;
import org.bukkit.configuration.Configuration;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author alexander
 */
public class JawaToolBox extends JavaPlugin{
    public static HashSet<UUID> maintenanceList;
    private static JawaToolBox plugin;
    public static Configuration config;
    public static boolean maintenanceMode;
    
    @Override
    public void onEnable(){
        plugin = this;
        maintenanceList = FileHandler.getMaintenanceList();
        this.saveDefaultConfig();
        config = this.getConfig();
        if (config.contains("maintenance-mode")){
            maintenanceMode = config.getBoolean("maintenance-mode");
        } else maintenanceMode = false;
        System.out.println("[JawaToolBox] Maintenance Mode: " + String.valueOf(maintenanceMode));
        
        getServer().getPluginManager().registerEvents(new PlayerPreJoin(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
        
        this.getCommand("mmadd").setExecutor(new MMAdd());
        this.getCommand("mmremove").setExecutor(new MMRemove());
        this.getCommand("mmtoggle").setExecutor(new MMToggle());
        this.getCommand("mmstatus").setExecutor(new MMStatus());
    }
    
    @Override
    public void onDisable(){
        maintenanceList.clear();
    }
    
    public static JawaToolBox getPlugin(){
        return plugin;
    }
}
