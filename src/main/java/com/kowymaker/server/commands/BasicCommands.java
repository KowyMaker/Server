package com.kowymaker.server.commands;

import java.net.InetSocketAddress;

import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.UpstreamMessageEvent;

import com.kowymaker.server.KowyMakerServer;
import com.kowymaker.server.annotations.Command;
import com.kowymaker.server.console.ServerConsole;
import com.kowymaker.server.core.net.codec.CodecResolver;
import com.kowymaker.server.core.net.handlers.MessageHandler;
import com.kowymaker.server.core.net.msg.DisconnectMessage;
import com.kowymaker.server.core.tasks.Task;
import com.kowymaker.server.game.players.Player;
import com.kowymaker.server.interfaces.CommandSender;

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
    
    @SuppressWarnings("unchecked")
    @Command(aliases = { "stress" }, desc = "Execute a stress test to determine server speed", max = 1, usage = "/<command> [num of tasks]")
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
            msg.setPlayer("StressTest");
            final MessageHandler<DisconnectMessage> handler = (MessageHandler<DisconnectMessage>) CodecResolver
                    .getHandler("disconnect");
            
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
                if (task != null)
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
}
