package com.kowymaker.server.core.net.handlers;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;

import com.kowymaker.server.core.Server;
import com.kowymaker.server.core.net.msg.DisconnectMessage;

public class DisconnectHandler extends MessageHandler<DisconnectMessage>
{
    
    @Override
    public boolean handle(Server server, ChannelHandlerContext ctx,
            MessageEvent e, DisconnectMessage msg) throws Exception
    {
        server.getChannels().write(msg);
        return true;
    }
    
}
