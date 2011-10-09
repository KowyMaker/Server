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
package com.kowymaker.server.core;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.group.DefaultChannelGroup;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

import com.kowymaker.server.KowyMakerServer;
import com.kowymaker.server.core.net.handlers.ConnectHandler;
import com.kowymaker.server.core.net.handlers.DisconnectHandler;
import com.kowymaker.server.core.tasks.TaskManager;
import com.kowymaker.spec.net.CodecResolver;

public class Server
{
    private final Logger          logger   = Logger.getLogger(Server.class
                                                   .getName());
    
    private final KowyMakerServer main;
    private final int             port;
    private final TaskManager     tasks;
    private ServerBootstrap       bootstrap;
    private Channel               channel;
    private final ChannelGroup    channels = new DefaultChannelGroup();
    
    public Server(KowyMakerServer main)
    {
        this.main = main;
        tasks = new TaskManager(this);
        port = main.getConfig().getInteger("server.port");
    }
    
    public KowyMakerServer getMain()
    {
        return main;
    }
    
    public int getPort()
    {
        return port;
    }
    
    public TaskManager getTasks()
    {
        return tasks;
    }
    
    public ServerBootstrap getBootstrap()
    {
        return bootstrap;
    }
    
    public boolean isRunning()
    {
        return main.isRunning();
    }
    
    public ChannelGroup getChannels()
    {
        return channels;
    }
    
    public Channel getChannel()
    {
        return channel;
    }
    
    @SuppressWarnings("unchecked")
    public void start()
    {
        logger.info("Starting server...");
        
        //Register handlers
        Map<String, Object> handlerProperties = new HashMap<String, Object>();
        handlerProperties.put("server", this);
        
        try
        {
            CodecResolver.registerHandler(handlerProperties,
                    ConnectHandler.class, DisconnectHandler.class);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        //Start server
        bootstrap = new ServerBootstrap(new NioServerSocketChannelFactory(
                Executors.newCachedThreadPool(),
                Executors.newCachedThreadPool()));
        bootstrap.setPipelineFactory(new ServerPipelineFactory(this));
        channel = bootstrap.bind(new InetSocketAddress(port));
        
        tasks.start();
        
        logger.info("Server started. Listening at port " + port);
    }
    
    public void stop()
    {
        channels.disconnect();
        bootstrap.releaseExternalResources();
    }
}
