package com.kowymaker.server.core.net.codec;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jdom.Element;

import com.kowymaker.server.core.net.handlers.ConnectHandler;
import com.kowymaker.server.core.net.msg.ConnectMessage;

public class ConnectCodec extends MessageCodec<ConnectMessage, ConnectHandler>
{
    
    public ConnectCodec()
    {
        super("connect");
    }
    
    @Override
    public ConnectMessage decode(ChannelHandlerContext ctx, Channel ch,
            Element xml)
    {
        final ConnectMessage msg = new ConnectMessage();
        msg.setName(xml.getChildText("name"));
        return msg;
    }
    
    @Override
    public Element encode(ConnectMessage msg)
    {
        final Element element = new Element("connect");
        final Element name = new Element("name");
        name.setText(msg.getName());
        element.addContent(name);
        return element;
    }
    
}
