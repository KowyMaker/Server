package com.kowymaker.server.core.net.handlers;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;

import com.kowymaker.server.core.Server;
import com.kowymaker.server.core.net.msg.Message;

public abstract class MessageHandler<T extends Message>
{
    public abstract boolean handle(Server server, ChannelHandlerContext ctx,
            MessageEvent e, T msg) throws Exception;
    
    @SuppressWarnings("unchecked")
    public void handle(Server server, ChannelHandlerContext context,
            MessageEvent e) throws Exception
    {
        handle(server, context, e, (T) e.getMessage());
    }
}
