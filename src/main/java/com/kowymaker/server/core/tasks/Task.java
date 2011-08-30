package com.kowymaker.server.core.tasks;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;

import com.kowymaker.server.core.Server;
import com.kowymaker.server.core.net.handlers.MessageHandler;
import com.kowymaker.server.core.net.msg.Message;

public class Task
{
    private final Server                            server;
    private final ChannelHandlerContext             context;
    private final MessageEvent                      event;
    private final Message                           message;
    private final MessageHandler<? extends Message> handler;
    private boolean                                 executed = false;
    
    public Task(Server server, ChannelHandlerContext context,
            MessageEvent event, Message message,
            MessageHandler<? extends Message> handler)
    {
        this.server = server;
        this.context = context;
        this.event = event;
        this.message = message;
        this.handler = handler;
    }
    
    public Server getServer()
    {
        return server;
    }
    
    public ChannelHandlerContext getContext()
    {
        return context;
    }
    
    public MessageEvent getEvent()
    {
        return event;
    }
    
    public Message getMessage()
    {
        return message;
    }
    
    public MessageHandler<? extends Message> getHandler()
    {
        return handler;
    }
    
    public boolean isExecuted()
    {
        return executed;
    }
    
    public void setExecuted(boolean executed)
    {
        this.executed = executed;
    }
}
