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
package com.kowymaker.server;

import java.io.IOException;
import java.util.Arrays;

import joptsimple.OptionException;
import joptsimple.OptionParser;
import joptsimple.OptionSet;

import com.kowymaker.server.commands.CommandsManager;
import com.kowymaker.server.console.ServerConsole;
import com.kowymaker.server.core.Server;
import com.kowymaker.server.data.Database;
import com.kowymaker.server.game.Game;
import com.kowymaker.server.utils.Configuration;
import com.kowymaker.server.utils.Version;

/**
 * @author Koka El Kiwi
 * 
 */
public class KowyMakerServer
{
    public static final Version APP_VERSION = new Version(0, 1, 0);
    private final Configuration config      = new Configuration();
    private Server              server;
    private ServerConsole       console;
    private Game                game;
    private Database            database;
    private CommandsManager     commandsManager;
    private boolean             running     = true;
    
    /**
     * Initialize configuration and parse args.
     * 
     * @param args
     */
    public KowyMakerServer(String[] args)
    {
        final OptionParser parser = new OptionParser() {
            {
                acceptsAll(Arrays.asList("?", "h", "help"), "Display help.");
                acceptsAll(Arrays.asList("v", "version"), "Display version.");
            }
        };
        
        OptionSet options = null;
        
        try
        {
            options = parser.parse(args);
        }
        catch (final OptionException e)
        {
            final StringBuffer sb = new StringBuffer();
            final Object[] opts = e.options().toArray();
            for (int i = 0; i < opts.length; i++)
            {
                sb.append(opts[i]);
                if (i < opts.length - 1)
                {
                    sb.append(", ");
                }
            }
            
            System.err.println("Unrecognized options: " + sb);
        }
        
        if (options == null || options.has("?"))
        {
            try
            {
                parser.printHelpOn(System.out);
            }
            catch (final IOException e)
            {
                e.printStackTrace();
            }
            System.exit(0);
        }
        else if (options.has("v"))
        {
            System.out.println("Kowy Maker - Server [v " + APP_VERSION + "]");
            System.exit(0);
        }
        
        try
        {
            config.load(
                    KowyMakerServer.class.getResourceAsStream("/config.yml"),
                    "yaml");
        }
        catch (final Exception e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * Initialize variables and start the server.
     * 
     * @throws Exception
     */
    public void init() throws Exception
    {
        System.out
                .println("Starting Kowy Maker Server v" + APP_VERSION + "...");
        
        // Initialize contents
        console = new ServerConsole(this);
        commandsManager = new CommandsManager(this);
        database = new Database(this);
        game = new Game(this);
        server = new Server(this);
        
        // Init Database
        database.registerSource(game);
        
        database.setup();
        if (!database.isDatabaseCreated())
        {
            database.createDatabase();
        }
        database.load();
        
        // Init Game Handler
        game.init();
        
        // Start servers
        server.start();
        
        System.out.println("Server ready for connection!");
    }
    
    /**
     * Get the server configuration.
     * 
     * @return Server configuration
     */
    public Configuration getConfig()
    {
        return config;
    }
    
    /**
     * Get the connections server.
     * 
     * @return Connections server
     */
    public Server getServer()
    {
        return server;
    }
    
    /**
     * Get console manager.
     * 
     * @return Console manager
     */
    public ServerConsole getConsole()
    {
        return console;
    }
    
    /**
     * Get game manager.
     * 
     * @return Game manager
     */
    public Game getGame()
    {
        return game;
    }
    
    /**
     * Get server database.
     * 
     * @return Server database
     */
    public Database getDatabase()
    {
        return database;
    }
    
    /**
     * Get commands manager.
     * 
     * @return Commands manager
     */
    public CommandsManager getCommandsManager()
    {
        return commandsManager;
    }
    
    /**
     * Verify if the server is running
     * 
     * @return true if the server is currently running else false
     */
    public boolean isRunning()
    {
        return running;
    }
    
    /**
     * Set the server running
     * 
     * @param running
     *            true or false.
     */
    public void setRunning(boolean running)
    {
        this.running = running;
    }
    
    /**
     * Shutdown the server
     */
    public void shutdown()
    {
        System.out.println("Server is shutting down...");
        running = false;
        server.stop();
        System.exit(0);
    }
    
    public static void main(String[] args)
    {
        final KowyMakerServer server = new KowyMakerServer(args);
        try
        {
            server.init();
        }
        catch (final Exception e)
        {
            e.printStackTrace();
        }
    }
    
}
