/*
 * Copyright (C) 2020 alexander
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.jawasystems.jawatoolbox.handlers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.jawasystems.jawatoolbox.JawaToolBox;
import org.bukkit.ChatColor;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Handles the data manipulation of the Message Of The Day.
 *
 * @author alexander
 */
public class MOTDHandler {

    private static JSONObject motd;
    private static int motdNormalCount;
    private static JawaToolBox plugin;
    private boolean motdEnabled;

    /** Construct the MOTDHanlder and configure it.
     * @param config
     * @param plugin 
     */
    public MOTDHandler(Configuration config, JawaToolBox plugin) {
        MOTDHandler.motdNormalCount = config.getInt("motd-normal-count", 4);
        MOTDHandler.motd = loadMOTD();
        MOTDHandler.plugin = plugin;
    }

    /** Loads the motd.json file into the motd object. If there is an error the exception
     * will be logged. If the file does not exist it will attempt to create an empty JSON file.
     * No matter what this will return a JSONObject. If there is a fatal error it will simply
     * return a blank JSONObject.
     * @return 
     */
    public static JSONObject loadMOTD() {
        File JSONFile = new File(plugin.getDataFolder() + "/motd.json");

        try {
            String source = new String(Files.readAllBytes(Paths.get(JSONFile.toURI())));
            return new JSONObject(source);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MOTDHandler.class.getName()).log(Level.INFO, "motd.json was not found in the config directory. Attempting to create a blank file.");
            boolean worked = saveMOTD(new JSONObject());
            if (worked) Logger.getLogger(MOTDHandler.class.getName()).log(Level.INFO, "motd.json created!");
            else Logger.getLogger(MOTDHandler.class.getName()).log(Level.INFO, "JawaToolBox was unable to generate the blank motd.json. This is likely a permissions problem.");
            return new JSONObject();
        } catch (IOException ex) {
            Logger.getLogger(MOTDHandler.class.getName()).log(Level.SEVERE, "Something went wrong JawaToolBox wasn't able to read the motd.json. Check directory permissions.");
            Logger.getLogger(MOTDHandler.class.getName()).log(Level.SEVERE, null, ex);
            return new JSONObject();
        }
    }

    /** Saves the MOTD object to file. This is the preferable method because erronious 
     * JSONObjects cannot be passed to it.
     * @return 
     */
    public static boolean saveMOTD() {
        return saveMOTD(motd);
    }

    /** Saves the MOTD object to file. Can also be used to create a blank MOTD by passing 
     * a new JSONObject to it. It is better to use saveMOTD() unless creating a new motd.json file.
     * @param motdObj
     * @return 
     */
    private static boolean saveMOTD(JSONObject motdObj) {
        File JSONFile = new File(plugin.getDataFolder() + "/motd.json");

        try ( //open our writer and write the player file
                PrintWriter writer = new PrintWriter(JSONFile)) {
            //System.out.println(obj);
            writer.print(motdObj.toString(4));
            writer.close();
            Logger.getLogger(MOTDHandler.class.getName()).log(Level.INFO, "Successfully updated motd.json");
            return true;
        } catch (FileNotFoundException ex) {

            Logger.getLogger(MOTDHandler.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    /** Adds the message to the motd and sends feed back to the player if it works or fails.
     * @param item
     * @param type
     */
    public static void addToMOTD(String item, String type, Player target) {
        boolean worked;
        switch (type) {
            case "priority": {
                if (!motd.has("priority")) {
                    motd.put("priority", new JSONArray());
                }
                motd.getJSONArray("priority").put(item);
                worked = saveMOTD();
                if (worked) target.sendMessage(ChatColor.GREEN + " > Message added to the MOTD Priority list.");
                else target.sendMessage(ChatColor.RED + " > Error: Unable to save the modified motd! Check with your server administrator!");
                break;
            }
            case "normal": {
                if (!motd.has("normal")) {
                    motd.put("normal", new JSONArray());
                }
                motd.getJSONArray("normal").put(item);
                worked = saveMOTD();
                if (worked) target.sendMessage(ChatColor.GREEN + " > Message added to the MOTD Normal list.");
                else target.sendMessage(ChatColor.RED + " > Error: Unable to save the modified motd! Check with your server administrator!");
                break;
            }
            case "title": {
                motd.put("title", item);
                worked = saveMOTD();
                if (worked) target.sendMessage(ChatColor.GREEN + " > Message added the title to the MOTD.");
                else target.sendMessage(ChatColor.RED + " > Error: Unable to save the modified motd! Check with your server administrator!");
                break;
            }
        }
    }

    /**
     * Transmits the MOTD to the player with the title, if set, the priority
     * messages, and the configured number of normal messages.
     *
     * @param target
     */
    public static void sendNormalMOTD(Player target) {
        String[] priority = new String[motd.getJSONArray("priority").length()];

        //Send motd title
        if (motd.has("title")) {
            target.sendMessage(ChatColor.translateAlternateColorCodes('&', motd.getString("title")));
        }

        //Send priority messages
        if (motd.has("priority")) {
            for (int i = 0; i > priority.length; i++) {
                priority[i] = ChatColor.GOLD + " > " + ChatColor.translateAlternateColorCodes('&', motd.getJSONArray("priority").getString(i));
            }
            target.sendMessage(priority);
        }

        //send a few random daily's
        if (motd.has("normal") && !motd.getJSONArray("normal").isEmpty()) {
            Random r = new Random();
            int totalNormalMessages;

            if (motdNormalCount > motd.getJSONArray("normal").length()) {
                totalNormalMessages = motd.getJSONArray("normal").length();
            } else {
                totalNormalMessages = motdNormalCount;
            }

            String[] normalMessages = new String[totalNormalMessages];
            JSONArray used = new JSONArray();
            int itemNum = r.nextInt(motd.getJSONArray("normal").length());
            normalMessages[0] = motd.getJSONArray("normal").getString(itemNum);
            used.put(itemNum);

            for (int i = 1; i < totalNormalMessages; i++) {
                while (used.toList().contains(itemNum)) {
                    itemNum = r.nextInt(motd.getJSONArray("normal").length());
                }
                normalMessages[i] = motd.getJSONArray("normal").getString(itemNum);
            }

            target.sendMessage(normalMessages);
        }

    }

    /**
     * Sends a formatted motd message to the player for edit review. This shows
     * all the motd messages and the title.
     *
     * @param target
     */
    public static void sendMOTDEdit(Player target) {
        target.sendMessage(ChatColor.GREEN + " > MOTD Title: " + ChatColor.translateAlternateColorCodes('&', motd.getString("title")));
        if (motd.has("priority")) {
            target.sendMessage(ChatColor.GREEN + " > " + ChatColor.GOLD + " Priority messages. These will always show to the player.");
            for (int i = 0; i < motd.getJSONArray("priorioty").length(); i++) {
                target.sendMessage(ChatColor.GREEN + " > " + ChatColor.AQUA + String.valueOf(i) + " " + ChatColor.translateAlternateColorCodes('&', motd.getJSONArray("priority").getString(i)));
            }
            if (motd.has("normal")) {
                target.sendMessage(ChatColor.GREEN + "--------------------------------------------------");
            }
        }

        if (motd.has("normal")) {
            target.sendMessage(ChatColor.GREEN + " > " + ChatColor.GOLD + " Normal messages. A random " + motdNormalCount + " will be show to players each time.");
            for (int i = 0; i < motd.getJSONArray("normal").length(); i++) {
                target.sendMessage(ChatColor.GREEN + " > " + ChatColor.AQUA + String.valueOf(i) + " " + ChatColor.translateAlternateColorCodes('&', motd.getJSONArray("normal").getString(i)));
            }
        }
    }

    /**
     * Removes a message from the MOTD object and saves it as well as handles
     * errors in the player's input. What syould be normal, title, or priority.
     *
     * @param what
     * @param index
     * @param target
     * @return
     */
    public static boolean removeFromMOTD(String what, int index, Player target) {
        switch (what) {
            case "title": {
                if (motd.has("title")) {
                    motd.remove("title");
                    saveMOTD();
                    target.sendMessage(ChatColor.GREEN + " > The title has been removed from the MOTD");
                } else {
                    target.sendMessage(ChatColor.RED + " > Error: The MOTD has no assigned title.");
                }
                break;
            }
            case "normal": {
                if (motd.has("normal")) {
                    if (index < motd.getJSONArray("normal").length() && index >= 0) {
                        motd.getJSONArray("normal").remove(index);
                        saveMOTD();
                        target.sendMessage(ChatColor.GREEN + " > Index " + index + " has been removed from the normal messages. Be sure to look before removing anymore as the indices may change!");
                    } else if (index == 0 && motd.getJSONArray("normal").length() == 1) {
                        motd.remove("normal");
                        saveMOTD();
                        target.sendMessage(ChatColor.GREEN + "> The last normal message has been removed");
                    } else if (index >= motd.getJSONArray("normal").length()) {
                        target.sendMessage(ChatColor.RED + " > Error: That index is larger than the number of messages!");
                    } else if (index < 0) {
                        target.sendMessage(ChatColor.GRAY + "*whisper* are you trying to break things with negative numbers? We properly error handle here, you have no power!");
                        target.sendMessage(ChatColor.GREEN + " > But really, you can't use negative indices");
                    }
                } else {
                    target.sendMessage(ChatColor.RED + " > Error: The MOTD has no normal messages to delete!");
                }
                break;
            }
            case "priority": {
                if (motd.has("priority")) {
                    if (index < motd.getJSONArray("priority").length() && index >= 0) {
                        motd.getJSONArray("priority").remove(index);
                        saveMOTD();
                        target.sendMessage(ChatColor.GREEN + " > Index " + index + " has been removed from the priority messages. Be sure to look before removing anymore as the indices may change!");
                    } else if (index == 0 && motd.getJSONArray("priority").length() == 1) {
                        motd.remove("priority");
                        saveMOTD();
                        target.sendMessage(ChatColor.GREEN + "> The last priority message has been removed");
                    } else if (index >= motd.getJSONArray("priority").length()) {
                        target.sendMessage(ChatColor.RED + " > Error: That index is larger than the number of messages!");
                    } else if (index < 0) {
                        target.sendMessage(ChatColor.GRAY + "*whisper* are you trying to break things with negative numbers? We properly error handle here, you have no power!");
                        target.sendMessage(ChatColor.GREEN + " > But really, you can't use negative indices");
                    }
                } else {
                    target.sendMessage(ChatColor.RED + " > Error: The MOTD has no priority messages to delete!");
                }
                break;
            }

        }
        return true;
    }

}
