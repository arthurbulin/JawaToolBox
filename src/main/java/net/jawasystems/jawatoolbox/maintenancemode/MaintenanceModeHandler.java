/*
 * The MIT License
 *
 * Copyright 2020 Jawamaster (Arthur Bulin).
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package net.jawasystems.jawatoolbox.maintenancemode;

import org.bukkit.entity.Player;

/**
 *
 * @author Jawamaster (Arthur Bulin)
 */
public class MaintenanceModeHandler {
    private static boolean maintenanceModeState;
    private static String maintenanceModeLevel;
    private static final String PERMISSIONNODE = "jawatoolbox.mm.";

    public enum MMLEVEL {
        NORMAL,
        LIST,
        RANKED,
        NUMERIC
    }
    
    public static void initializeMM(boolean state, String level){
        maintenanceModeState = state;
        maintenanceModeLevel = level;
    }
    
    public static boolean getMMStatus(){
        return maintenanceModeState;
    }
    
    public static void setMMStatus(boolean status){
        maintenanceModeState = status;
    }
    
    public static String getMMLevel(){
        return maintenanceModeLevel;
    }
    
    public static void setMMLevel(String level){
        maintenanceModeLevel = level;
    }
    
    public static void evaluateUser(Player player){
        if (getMMStatus()){
            if (!maintenanceModeLevel.equalsIgnoreCase("list") && !player.hasPermission(PERMISSIONNODE + getMMLevel())){
                player.kickPlayer("This server is currently in maintenance mode. Only authorized personel may join.");
            }
        }
    }
            
}
