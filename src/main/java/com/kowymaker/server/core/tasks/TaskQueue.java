package com.kowymaker.server.core.tasks;

import java.util.ArrayList;
import java.util.List;

public class TaskQueue
{
    private final List<Task> tasks = new ArrayList<Task>();
    
    public synchronized boolean add(Task task)
    {
        return tasks.add(task);
    }
    
    public synchronized Task remove()
    {
        Task task = null;
        
        if (!tasks.isEmpty())
        {
            task = tasks.remove(0);
        }
        
        return task;
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
