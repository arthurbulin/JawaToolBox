/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jawasystems.jawatoolbox;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.jawasystems.jawatoolbox.handlers.FileHandler;
import net.jawasystems.jawatoolbox.handlers.MOTDHandler;
import net.jawasystems.jawatoolbox.sleepvote.PlayerBedEnter;
import net.jawasystems.jawatoolbox.listeners.PlayerBedLeave;
import net.jawasystems.jawatoolbox.listeners.PlayerDeathListener;
import net.jawasystems.jawatoolbox.listeners.PlayerJoin;
import net.jawasystems.jawatoolbox.listeners.PlayerPreJoin;
import net.jawasystems.jawatoolbox.maintenancemode.MMAdd;
import net.jawasystems.jawatoolbox.maintenancemode.MMRemove;
import net.jawasystems.jawatoolbox.maintenancemode.MMStatus;
import net.jawasystems.jawatoolbox.maintenancemode.MMToggle;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author alexander
 */
public class JawaToolBox extends JavaPlugin{
    public static HashSet<UUID> maintenanceList;
    public static HashSet<UUID> sleepList;
    private static JawaToolBox plugin;
    private static Configuration config;
    public static boolean maintenanceMode;
    private static FileConfiguration changelog;
   
    
    private static MOTDHandler motdHandler;

    @Override
    public void onEnable(){
        plugin = this;
        loadChangeLog();
        sleepList = new HashSet();
        maintenanceList = FileHandler.getMaintenanceList();
        this.saveDefaultConfig();
        config = this.getConfig();
        maintenanceMode = config.getBoolean("maintenance-mode", false);

        System.out.println("[JawaToolBox] Maintenance Mode: " + String.valueOf(maintenanceMode));
        
        motdHandler = new MOTDHandler(config, this);
        
        //TODO add a command that gives entity data in the surrounding area
        
        getServer().getPluginManager().registerEvents(new PlayerPreJoin(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
        getServer().getPluginManager().registerEvents(new PlayerBedEnter(), this);
        getServer().getPluginManager().registerEvents(new PlayerBedLeave(), this);
        
        //Protects players from void deaths to an extent
        getServer().getPluginManager().registerEvents(new PlayerDeathListener(), this);
        
        this.getCommand("mmadd").setExecutor(new MMAdd());
        this.getCommand("mmremove").setExecutor(new MMRemove());
        this.getCommand("mmtoggle").setExecutor(new MMToggle());
        this.getCommand("mmstatus").setExecutor(new MMStatus());
        
        this.getCommand("motd").setExecutor(new MOTD());
        
        this.getCommand("jawatoolbox").setExecutor(new JawaToolBoxAbout());
    }
    
    @Override
    public void onDisable(){
        maintenanceList.clear();
    }
    
    public static JawaToolBox getPlugin(){
        return plugin;
    }
    
    public static MOTDHandler getMOTDHandler(){
        return motdHandler;
    }
    
    public static Configuration getConfiguration(){
        return config;
    }
    
    private static void loadChangeLog() {
        InputStream io = JawaToolBox.getPlugin().getResource("ChangeLog.yml");
        
        changelog = new YamlConfiguration();
        try {
            changelog.load(new InputStreamReader(io));
            Logger.getLogger("JawaToolBox").info("ChangeLog has been loaded.");
        } catch (IOException ex) {
            Logger.getLogger(JawaToolBoxAbout.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidConfigurationException ex) {
            Logger.getLogger(JawaToolBoxAbout.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static FileConfiguration getChangeLog(){
        return changelog;
    }
}
