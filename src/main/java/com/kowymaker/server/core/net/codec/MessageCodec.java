package com.kowymaker.server.core.net.codec;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jdom.Element;

import com.kowymaker.server.core.net.handlers.MessageHandler;
import com.kowymaker.server.core.net.msg.Message;

public abstract class MessageCodec<T extends Message, V extends MessageHandler<T>>
{
    private final String opcode;
    
    public MessageCodec(String opcode)
    {
        this.opcode = opcode;
    }
    
    public String getOpcode()
    {
        return opcode;
    }
    
    public abstract T decode(ChannelHandlerContext ctx, Channel ch, Element xml);
    
    public abstract Element encode(T msg);
}
