package com.kowymaker.server.core.tasks;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.kowymaker.server.core.Server;

public class TaskManager extends Thread
{
    private final Server    server;
    private final TaskQueue tasks  = new TaskQueue();
    
    Executor                worker = Executors.newCachedThreadPool();
    
    public TaskManager(Server server)
    {
        this.server = server;
    }
    
    @Override
    public void run()
    {
        while (server.isRunning())
        {
            final Task task = tasks.remove();
            if (task != null)
            {
                final TaskExecutor executor = new TaskExecutor(task);
                worker.execute(executor);
            }
        }
    }
    
    public synchronized boolean add(Task task)
    {
        boolean added = false;
        try
        {
            added = tasks.add(task);
        }
        catch (final Exception e)
        {
            e.printStackTrace();
        }
        return added;
    }
    
    public synchronized int size()
    {
        return tasks.size();
    }
    
    public synchronized boolean isEmpty()
    {
        return tasks.isEmpty();
    }
}
