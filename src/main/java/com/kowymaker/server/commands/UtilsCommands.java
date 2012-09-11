package com.kowymaker.server.commands;

import java.util.concurrent.Semaphore;

import com.kowymaker.server.KowyMakerServer;
import com.kowymaker.server.annotations.Command;
import com.kowymaker.server.interfaces.CommandSender;

import etm.core.configuration.BasicEtmConfigurator;
import etm.core.configuration.EtmManager;
import etm.core.monitor.EtmMonitor;
import etm.core.monitor.EtmPoint;
import etm.core.renderer.SimpleTextRenderer;

public class UtilsCommands
{
    @Command(aliases = { "stress" }, desc = "Execute a stress test to determine server tasks executing speed", max = 1, usage = "/<command> [num of tasks]")
    public static boolean stress(KowyMakerServer main, CommandContext command,
            CommandSender sender)
    {
        BasicEtmConfigurator.configure();
        final EtmMonitor monitor = EtmManager.getEtmMonitor();
        monitor.start();
        
        int num = 100000;
        if (command.argsLength() > 0)
        {
            num = command.getInteger(0);
        }
        if (num < 50)
        {
            num = 50;
        }
        
        sender.sendMessage("Starting Stress Test with " + num + " tasks.");
        
        EtmPoint point = monitor.createPoint("Kowy Maker - Stress test");
        Counter counter = new Counter(num);
        
        for (int i = 0; i < num; i++)
        {
            main.getServer().getTasks()
                    .add(new StressRunnable(monitor, point, counter));
        }
        
        return true;
    }
    
    public static class StressRunnable implements Runnable
    {
        private final EtmMonitor monitor;
        private final EtmPoint   point;
        private final Counter    counter;
        
        public StressRunnable(EtmMonitor monitor, EtmPoint point,
                Counter counter)
        {
            this.monitor = monitor;
            this.point = point;
            this.counter = counter;
        }
        
        public void run()
        {
            point.collect();
            
            try
            {
                if (counter.decrement())
                {
                    monitor.render(new SimpleTextRenderer());
                    
                    monitor.stop();
                }
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }
    
    public static class Counter
    {
        int       myCount;
        Semaphore sem = new Semaphore(1);
        
        public Counter()
        {
            myCount = 0;
        }
        
        public Counter(int init)
        {
            myCount = init;
        }
        
        public synchronized int getValue()
        {
            return myCount;
        }
        
        public void clear()
        {
            myCount = 0;
        }
        
        public boolean decrement() throws InterruptedException
        {
            sem.acquire();
            myCount--;
            sem.release();
            
            if (myCount == 0)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        
        public String toString()
        {
            return "" + myCount;
        }
    }
}
