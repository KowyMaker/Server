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

public class TaskExecutor implements Runnable
{
    private final Task task;
    
    public TaskExecutor(Task task)
    {
        this.task = task;
    }
    
    public void run()
    {
        try
        {
            task.getHandler().handle(task.getContext(), task.getEvent());
            task.done(true);
        }
        catch (final Exception e)
        {
            e.printStackTrace();
        }
    }
}
