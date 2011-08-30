package com.kowymaker.server.console.commands;

import com.kowymaker.server.KowyMakerServer;

public class UtilsCommand extends Command
{
    public UtilsCommand()
    {
        super("threads");
    }
    
    @Override
    public void execute(KowyMakerServer main, String[] command)
    {
        if(command[0].equalsIgnoreCase("threads"))
        {
            for(Thread thread : Thread.getAllStackTraces().keySet())
            {
                StringBuffer sb = new StringBuffer();
                sb.append("Thread #");
                sb.append(thread.getId());
                sb.append(':');
                sb.append(System.getProperty("line.separator"));
                sb.append('\t');
                sb.append("Name: ");
                sb.append(thread.getName());
                sb.append(System.getProperty("line.separator"));
                sb.append('\t');
                sb.append("Priority: ");
                sb.append(thread.getPriority());
                sb.append(System.getProperty("line.separator"));
                sb.append('\t');
                sb.append("Alive: ");
                sb.append(thread.isAlive());
                sb.append(System.getProperty("line.separator"));
                sb.append('\t');
                sb.append("Daemon: ");
                sb.append(thread.isDaemon());
                sb.append(System.getProperty("line.separator"));
                sb.append('\t');
                sb.append("Interrupted: ");
                sb.append(thread.isInterrupted());
                sb.append(System.getProperty("line.separator"));
                sb.append('\t');
                sb.append("State: ");
                sb.append(thread.getState());
                sb.append(System.getProperty("line.separator"));
                sb.append('\t');
                sb.append("ThreadGroup name: ");
                sb.append(thread.getThreadGroup().getName());
                sb.append(System.getProperty("line.separator"));
                sb.append('\t');
                sb.append("Current: ");
                sb.append(thread.equals(Thread.currentThread()));
                
                System.out.println(sb.toString());
            }
        }
    }
    
}
