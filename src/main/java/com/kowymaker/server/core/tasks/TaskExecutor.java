package com.kowymaker.server.core.tasks;

public class TaskExecutor implements Runnable
{
    private final Task task;
    
    public TaskExecutor(Task task)
    {
        this.task = task;
    }
    
    @Override
    public void run()
    {
        try
        {
            task.getHandler().handle(task.getServer(), task.getContext(),
                    task.getEvent());
            task.setExecuted(true);
        }
        catch (final Exception e)
        {
            e.printStackTrace();
        }
    }
}
