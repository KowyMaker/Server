package com.kowymaker.server.console.commands;

import java.net.InetSocketAddress;

import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.UpstreamMessageEvent;

import com.kowymaker.server.KowyMakerServer;
import com.kowymaker.server.core.net.codec.CodecResolver;
import com.kowymaker.server.core.net.handlers.MessageHandler;
import com.kowymaker.server.core.net.msg.DisconnectMessage;
import com.kowymaker.server.core.tasks.Task;

public class StressTestCommand extends Command
{
    public StressTestCommand()
    {
        super("stress");
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public void execute(KowyMakerServer main, String[] command)
    {
        int num = 100000;
        final Task[] tasks = new Task[num];
        if (command.length > 1)
        {
            num = Integer.parseInt(command[1]);
        }
        if (num < 50)
        {
            num = 50;
        }
        System.out.println("Starting Stress Test with " + num + " tasks.");
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
        System.out.println("Diff: " + diff + " ms -- Speed: " + speed
                + " ops/s");
    }
    
}
