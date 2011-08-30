package com.kowymaker.server.core.net.codec;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jdom.Element;

import com.kowymaker.server.core.net.handlers.DisconnectHandler;
import com.kowymaker.server.core.net.msg.DisconnectMessage;

public class DisconnectCodec extends
        MessageCodec<DisconnectMessage, DisconnectHandler>
{
    
    public DisconnectCodec()
    {
        super("disconnect");
    }
    
    @Override
    public DisconnectMessage decode(ChannelHandlerContext ctx, Channel ch,
            Element xml)
    {
        final DisconnectMessage msg = new DisconnectMessage();
        msg.setPlayer(ch.getRemoteAddress().toString());
        return msg;
    }
    
    @Override
    public Element encode(DisconnectMessage msg)
    {
        final Element element = new Element("disconnect");
        element.addContent(new Element("player").setText(msg.getPlayer()));
        return element;
    }
    
}
