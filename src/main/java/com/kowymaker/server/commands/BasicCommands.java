package com.kowymaker.server.commands;

import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.UpstreamMessageEvent;

import com.kowymaker.server.KowyMakerServer;
import com.kowymaker.server.annotations.Command;
import com.kowymaker.server.console.ServerConsole;
import com.kowymaker.server.core.tasks.Task;
import com.kowymaker.server.game.players.Player;
import com.kowymaker.server.interfaces.CommandSender;
import com.kowymaker.spec.net.CodecResolver;
import com.kowymaker.spec.net.MessageHandler;
import com.kowymaker.spec.net.msg.DisconnectMessage;
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
    
    @Command(aliases = { "stress" }, desc = "Execute a stress test to determine server tasks executing speed", max = 1, usage = "/<command> [num of tasks]")
    public static boolean stress(KowyMakerServer main, CommandContext command,
            CommandSender sender)
    {
        int num = 100000;
        final Task[] tasks = new Task[num];
        if (command.argsLength() > 0)
        {
            num = command.getInteger(0);
        }
        if (num < 50)
        {
            num = 50;
        }
        sender.sendMessage("Starting Stress Test with " + num + " tasks.");
        final long start = System.currentTimeMillis();
        for (int i = 0; i < num; i++)
        {
            final DisconnectMessage msg = new DisconnectMessage();
            msg.setName("StressTest");
            final MessageHandler<DisconnectMessage> handler = (MessageHandler<DisconnectMessage>) CodecResolver
                    .getHandler(DisconnectMessage.class);
            
            final MessageEvent event = new UpstreamMessageEvent(main
                    .getServer().getChannel(), msg, new InetSocketAddress(6563));
            final Task task = new Task(main.getServer(), null, event, msg,
                    handler);
            main.getServer().getTasks().add(task);
            tasks[i] = task;
        }
        boolean empty;
        do
        {
            empty = true;
            for (final Task task : tasks)
            {
                if (task != null && empty)
                {
                    if (!task.isExecuted())
                    {
                        empty = false;
                    }
                }
            }
        } while (!empty);
        final long end = System.currentTimeMillis();
        
        final long diff = end - start;
        double speed = Double.NaN;
        if (diff > 0)
        {
            speed = num / diff * 1000;
        }
        sender.sendMessage("Diff: " + diff + " ms -- Speed: " + speed
                + " ops/s");
        return true;
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
                if(i < commands.size() - 1)
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
            if(method != null)
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
