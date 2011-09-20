package com.kowymaker.server.core.net.codec;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jdom.Element;

import com.kowymaker.server.core.net.handlers.ChatHandler;
import com.kowymaker.server.core.net.msg.ChatMessage;

public class ChatCodec extends MessageCodec<ChatMessage, ChatHandler>
{
    
    public ChatCodec()
    {
        super("message");
    }
    
    @Override
    public ChatMessage decode(ChannelHandlerContext ctx, Channel ch, Element xml)
    {
        final ChatMessage msg = new ChatMessage();
        msg.setMessage(xml.getText());
        
        return msg;
    }
    
    @Override
    public Element encode(ChatMessage msg)
    {
        final Element element = new Element("message");
        element.setText(msg.getMessage());
        
        return element;
    }
    
}
