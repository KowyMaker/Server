package com.kowymaker.server.console;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.kowymaker.server.KowyMakerServer;
import com.kowymaker.server.console.commands.Command;
import com.kowymaker.server.console.commands.CommandResolver;

public class ServerConsole
{
    private final KowyMakerServer    main;
    private final Logger             global    = Logger.getLogger("");
    
    private final LoggerOutputStream outStream = new LoggerOutputStream(global,
                                                       Level.INFO);
    private final LoggerOutputStream errStream = new LoggerOutputStream(global,
                                                       Level.SEVERE);
    
    public ServerConsole(KowyMakerServer main)
    {
        this.main = main;
        try
        {
            // Setup Console Reader
            final ThreadCommandReader thread = new ThreadCommandReader(this);
            thread.setDaemon(true);
            thread.start();
            
            // Setup Logger
            for (final Handler handler : global.getHandlers())
            {
                global.removeHandler(handler);
            }
            
            final ConsoleHandler console = new TerminalConsoleHandler(this);
            global.addHandler(console);
            
            final File logFile = new File("server.log");
            final FileHandler fileHandler = new FileHandler(
                    logFile.getAbsolutePath(), true);
            fileHandler.setFormatter(new LogFormatter());
            global.addHandler(fileHandler);
            
            System.setOut(new PrintStream(outStream, true));
            System.setErr(new PrintStream(errStream, true));
        }
        catch (final IOException e)
        {
            e.printStackTrace();
        }
    }
    
    public boolean isRunning()
    {
        return main.isRunning();
    }
    
    public void handle(String commandCode)
    {
        final String[] c = commandCode.split(" ");
        final Command command = CommandResolver.getCommand(c[0]);
        if (command != null)
        {
            command.execute(main, c);
        }
    }
}
