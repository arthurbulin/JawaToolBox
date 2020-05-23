/*
 * Copyright (C) 2019 alexander
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
package net.jawasystems.jawatoolbox;

import java.util.Arrays;
import java.util.HashSet;
import net.jawasystems.jawatoolbox.handlers.MOTDHandler;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author alexander
 */
public class MOTD implements CommandExecutor {

    private final String TOOSHORT = ChatColor.RED + " > That command requires more aguments!";
    private final String BADTYPE = ChatColor.RED + " > That is not a valid type. The type are normal, priority, and title.";
    private final String BADINT = ChatColor.RED + " > An integer argument is required!";

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        String usage
                = "Get the motd: /motd"
                //+ "Edit: /motd edit <type> <message number> <message>"
                + "Add: /motd add <type> <message>"
                + "Remove: /motd rem <type> <message number>"
                + "Help: /motd help";

        Player target = (Player) commandSender;
        HashSet<String> types = new HashSet(Arrays.asList("normal", "priority", "title"));

        if (args == null || args.length == 0) {
            MOTDHandler.sendNormalMOTD(target);
        } else if (args.length == 1) {
            if (!commandSender.hasPermission("jawatoolbox.motd.admin")) {
                commandSender.sendMessage(ChatColor.RED + " > You do not have permission to perform admin functions on the MOTD.");
                return true;
            }
            switch (args[0].toLowerCase()) {
                case "edit": {
                    MOTDHandler.sendMOTDEdit(target);
                    break;
                }
                case "help": {
                    target.sendMessage(usage);
                    break;
                }
                default: {
                    target.sendMessage(ChatColor.RED + " > That argument is not understood.");
                    target.sendMessage(usage);
                    break;
                }
            }
        } else if (args.length > 1) {
            if (!commandSender.hasPermission("jawatoolbox.motd.admin")) {
                commandSender.sendMessage(ChatColor.RED + " > You do not have permission to perform admin functions on the MOTD.");
                return true;
            }
            switch (args[0].toLowerCase()) {
                case "add": { //motd add <type> <message...>
                    if (args.length < 3) {
                        commandSender.sendMessage(TOOSHORT);
                        return true;
                    }
                    if (!types.contains(args[1].toLowerCase())) {
                        commandSender.sendMessage(BADTYPE);
                        return true;
                    }

                    MOTDHandler.addToMOTD(String.join(" ", Arrays.copyOfRange(args, 2, args.length, String[].class)), args[1], target);
                    break;
                }
                case "rem": { ///motd rem <type> <message number>
                    if (args.length < 2) {
                        commandSender.sendMessage(TOOSHORT);
                        return true;
                    }
                    if (!types.contains(args[1].toLowerCase())) {
                        commandSender.sendMessage(BADTYPE);
                        return true;
                    }

                    if (!args[1].equalsIgnoreCase("title")) {
                        try {
                            Integer.valueOf(args[2]);
                        } catch (NumberFormatException ex) {
                            commandSender.sendMessage(BADINT);
                            return true;
                        }
                        MOTDHandler.removeFromMOTD(args[1].toLowerCase(), Integer.valueOf(args[2]), target);
                    } else {
                        MOTDHandler.removeFromMOTD(args[1].toLowerCase(), 0, target);
                    }
                    break;
                }
            }
        }

        //HashMap<String,String> parsed = ArgumentParser.getArgumentValues(args);
        //evaluate arguments
        //check permissions
        //execute values
        return true;
    }

}
