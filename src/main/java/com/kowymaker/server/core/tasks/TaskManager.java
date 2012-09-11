/**
 * This file is part of Kowy Maker.
 * 
 * Kowy Maker is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * Kowy Maker is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * Kowy Maker. If not, see <http://www.gnu.org/licenses/>.
 */
package com.kowymaker.server.core.tasks;

import java.util.Deque;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.google.common.collect.Queues;
import com.kowymaker.server.core.Server;

public class TaskManager extends Thread
{
    private final Server          server;
    private final Deque<Runnable> tasks  = Queues.newArrayDeque();
    
    Executor                      worker = Executors.newCachedThreadPool();
    
    public TaskManager(Server server)
    {
        this.server = server;
    }
    
    @Override
    public void run()
    {
        while (server.isRunning())
        {
            final Runnable task = tasks.poll();
            if (task != null)
            {
                worker.execute(task);
            }
        }
    }
    
    public synchronized boolean add(Task task)
    {
        TaskExecutor executor = new TaskExecutor(task);
        return add(executor);
    }
    
    public synchronized boolean add(Runnable task)
    {
        boolean added = false;
        try
        {
            added = tasks.offer(task);
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
