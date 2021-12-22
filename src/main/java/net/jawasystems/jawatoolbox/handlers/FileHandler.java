/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jawasystems.jawatoolbox.handlers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashSet;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.jawasystems.jawatoolbox.JawaToolBox;
import org.json.JSONObject;

/**
 *
 * @author alexander
 */
public class FileHandler {
    private static final Logger LOGGER = Logger.getLogger("FileHandler");
    
    public static HashSet getMaintenanceList(){
        File maintenanceList = new File(JawaToolBox.getPlugin().getDataFolder() + "/maintenancelist.txt");
        HashSet<UUID> mList = new HashSet(); //return this
        
        if (maintenanceList.exists()) {             try { //Proceed if the file exists
            BufferedReader reader; 
            
            try { //Try to read the file
                reader = new BufferedReader(new FileReader(maintenanceList));
            } catch (FileNotFoundException ex) {
                Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, ex);
                LOGGER.log(Level.SEVERE, "A problem was encountered reading maintenancelist.txt. No UUIDS loaded.");
                return mList; //short circuit it here
            }
            
            String line = reader.readLine();
            UUID uuid;
            
            while (line != null) {
                try {
                    uuid = UUID.fromString(line);
                } catch (IllegalArgumentException ex) {
                    LOGGER.log(Level.WARNING, "{0} is an invalid UUID. Please check your maintenancelist.txt", line);
                    continue;
                }

                mList.add(uuid);
                line = reader.readLine();
            }
            reader.close();
            LOGGER.log(Level.INFO, "{0} UUIDs loaded into the maintenance list.", mList.size());
            
        } catch (IOException ex) {
                Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, ex);
                return mList;
            }
            
        } else LOGGER.log(Level.INFO, "No maintenancelist.txt exists. No UUIDS loaded.");
        return mList;
    }
    
    public static void saveMaintenanceList(){
        File maintenanceList = new File(JawaToolBox.getPlugin().getDataFolder() + "/maintenancelist.txt");
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(maintenanceList);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos));
            
            for (UUID uuid : JawaToolBox.maintenanceList){
                writer.write(uuid.toString());
                writer.newLine();
            }
            
            writer.close();
        } catch (FileNotFoundException ex) {
            LOGGER.log(Level.WARNING,"Something went wrong and the maintenancelist.txt file wasn't found.");
            Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            LOGGER.log(Level.WARNING,"Something went wrong and the maintenancelist.txt file couldn't be written too.");
            Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
}
