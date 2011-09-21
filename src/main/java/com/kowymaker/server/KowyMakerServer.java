/**
 * 
 */
package com.kowymaker.server;

import java.io.IOException;
import java.util.Arrays;

import joptsimple.OptionException;
import joptsimple.OptionParser;
import joptsimple.OptionSet;

import com.kowymaker.policyserver.PolicyServer;
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
    private PolicyServer        policyServer;
    private ServerConsole       console;
    private Game                game;
    private Database            database;
    private CommandsManager     commandsManager;
    private boolean             running     = true;
    
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
    
    public void init() throws Exception
    {
        console = new ServerConsole(this);
        commandsManager = new CommandsManager(this);
        
        System.out
                .println("Starting Kowy Maker Server v" + APP_VERSION + "...");
        
        server = new Server(this);
        policyServer = new PolicyServer(config.getInteger("server.port") + 1);
        
        database = new Database(this);
        database.setup();
        
        game = new Game(this);
        game.init();
        
        server.start();
        policyServer.start();
        
        System.out.println("Server ready for connection!");
    }
    
    public Configuration getConfig()
    {
        return config;
    }
    
    public Server getServer()
    {
        return server;
    }
    
    public PolicyServer getPolicyServer()
    {
        return policyServer;
    }
    
    public ServerConsole getConsole()
    {
        return console;
    }
    
    public Game getGame()
    {
        return game;
    }
    
    public Database getDatabase()
    {
        return database;
    }
    
    public CommandsManager getCommandsManager()
    {
        return commandsManager;
    }

    public void setCommandsManager(CommandsManager commandsManager)
    {
        this.commandsManager = commandsManager;
    }

    public boolean isRunning()
    {
        return running;
    }
    
    public void setRunning(boolean running)
    {
        this.running = running;
    }
    
    public void shutdown()
    {
        System.out.println("Server is shutting down...");
        running = false;
        policyServer.setListening(false);
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
