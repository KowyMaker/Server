package com.kowymaker.server.console.commands;

import com.kowymaker.server.KowyMakerServer;

public abstract class Command
{
    protected final String[] commands;
    
    public Command(String... commands)
    {
        this.commands = commands;
    }
    
    public String[] getCommands()
    {
        return commands;
    }
    
    public abstract void execute(KowyMakerServer main, String[] command);
}
