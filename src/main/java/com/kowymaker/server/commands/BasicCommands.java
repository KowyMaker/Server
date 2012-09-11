package com.kowymaker.server.commands;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.kowymaker.server.KowyMakerServer;
import com.kowymaker.server.annotations.Command;
import com.kowymaker.server.console.ServerConsole;
import com.kowymaker.server.game.players.Player;
import com.kowymaker.server.interfaces.CommandSender;
import com.kowymaker.spec.utils.SystemUtils;

/**
 * Basics commands for the server: stop, say and stress (others will be
 * available)
 * 
 * @author Koka El Kiwi
 * 
 */
public class BasicCommands
{
    @Command(aliases = { "stop", "quit" }, max = 0, desc = "Stop the server")
    public static boolean stop(KowyMakerServer main, CommandContext command,
            CommandSender sender)
    {
        if (sender instanceof ServerConsole)
        {
            main.shutdown();
            return true;
        }
        return false;
    }
    
    @Command(aliases = { "say" }, min = 1, desc = "Send a message to all players", usage = "/<command> [message]")
    public static boolean say(KowyMakerServer main, CommandContext command,
            CommandSender sender)
    {
        for (final Player player : main.getGame().getPlayers().getPlayers()
                .values())
        {
            player.sendMessage(command.getJoinedString(0));
        }
        return true;
    }
    
    @Command(aliases = { "help" }, max = 1, desc = "Display help", usage = "/<command> [command]")
    public static boolean help(KowyMakerServer main, CommandContext command,
            CommandSender sender)
    {
        if (command.argsLength() == 0)
        {
            List<String> commands = new ArrayList<String>();
            List<String> descs = new ArrayList<String>();
            int minSize = 0;
            for (Method method : main.getCommandsManager().getAliases()
                    .values())
            {
                Command cmd = method.getAnnotation(Command.class);
                StringBuffer sb = new StringBuffer();
                for (int i = 0; i < cmd.aliases().length; i++)
                {
                    sb.append(cmd.aliases()[i]);
                    if (i < cmd.aliases().length - 1)
                    {
                        sb.append(',');
                    }
                }
                String desc = cmd.usage().replace("<command>",
                        "[" + sb.toString() + "]");
                commands.add(desc);
                descs.add(cmd.desc());
                if (desc.length() > minSize)
                {
                    minSize = desc.length();
                }
            }
            
            StringBuffer sb = new StringBuffer();
            sb.append("/================ HELP ===================/");
            sb.append(SystemUtils.getSystemOS().getLineSeparator());
            for (int i = 0; i < commands.size(); i++)
            {
                sb.append(commands.get(i));
                for (int j = 0; j < minSize - commands.get(i).length(); j++)
                {
                    sb.append(' ');
                }
                sb.append("  :  ");
                sb.append(descs.get(i));
                if (i < commands.size() - 1)
                {
                    sb.append(SystemUtils.getSystemOS().getLineSeparator());
                }
            }
            sender.sendMessage(sb.toString());
        }
        else
        {
            String c = command.getString(0);
            Method method = main.getCommandsManager().getAliases().get(c);
            if (method != null)
            {
                Command cmd = method.getAnnotation(Command.class);
                sender.sendMessage("Usage: "
                        + cmd.usage().replace("<command>", c));
            }
            else
            {
                sender.sendMessage("Command didn't exists!");
            }
            
        }
        
        return true;
    }
}
