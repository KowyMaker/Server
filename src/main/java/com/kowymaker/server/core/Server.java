package com.kowymaker.server.core;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.group.DefaultChannelGroup;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

import com.kowymaker.server.KowyMakerServer;
import com.kowymaker.server.core.tasks.TaskManager;

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
    
    public void start()
    {
        logger.info("Starting server...");
        
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
