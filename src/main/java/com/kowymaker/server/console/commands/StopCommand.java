package com.kowymaker.server.console.commands;

import com.kowymaker.server.KowyMakerServer;

public class StopCommand extends Command
{
    
    public StopCommand()
    {
        super("stop", "quit");
    }
    
    @Override
    public void execute(KowyMakerServer main, String[] command)
    {
        main.shutdown();
    }
    
}
