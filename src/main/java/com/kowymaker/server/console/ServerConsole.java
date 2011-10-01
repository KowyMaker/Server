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
import com.kowymaker.server.interfaces.CommandSender;

public class ServerConsole implements CommandSender
{
    private final KowyMakerServer    main;
    private final Logger             global    = Logger.getLogger("");
    
    public static final DebugLevel   DEBUG     = new DebugLevel();
    
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
                    logFile.getAbsolutePath(), 500000, 5, true);
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
        main.getCommandsManager().execute(this, commandCode);
    }
    
    @Override
    public void sendMessage(String message)
    {
        System.out.println(message);
    }
    
    public static class DebugLevel extends Level
    {
        private static final long serialVersionUID = 4164250706426396472L;
        
        protected DebugLevel()
        {
            super("DEBUG", Level.INFO.intValue() + 53);
        }
        
    }
}
