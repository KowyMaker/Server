package com.kowymaker.server.core.net;

import java.nio.charset.Charset;

import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import com.kowymaker.server.core.net.codec.CodecResolver;
import com.kowymaker.server.core.net.codec.MessageCodec;
import com.kowymaker.server.core.net.handlers.MessageHandler;
import com.kowymaker.server.core.net.msg.Message;

public class ServerEncoder extends OneToOneEncoder
{
    
    @SuppressWarnings({ "unchecked" })
    @Override
    protected Object encode(ChannelHandlerContext ctx, Channel channel,
            Object msg) throws Exception
    {
        if (msg instanceof Message)
        {
            final Message message = (Message) msg;
            final MessageCodec<Message, ? extends MessageHandler<? extends Message>> codec = (MessageCodec<Message, ? extends MessageHandler<? extends Message>>) CodecResolver
                    .getCodec(message.getClass());
            if (codec == null)
            {
                throw new NullPointerException("codec");
            }
            
            final XMLOutputter output = new XMLOutputter(
                    Format.getCompactFormat());
            final Element result = codec.encode(message);
            final String encoded = output.outputString(result) + '\0';
            
            return ChannelBuffers.copiedBuffer(encoded.getBytes(Charset
                    .defaultCharset()));
        }
        
        return null;
    }
    
}
