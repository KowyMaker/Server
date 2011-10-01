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
