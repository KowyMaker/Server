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
package com.kowymaker.server.core.net;

import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;

import com.kowymaker.spec.net.CodecResolver;
import com.kowymaker.spec.net.codec.MessageCodec;
import com.kowymaker.spec.net.msg.Message;
import com.kowymaker.spec.utils.data.DataBuffer;
import com.kowymaker.spec.utils.data.DynamicDataBuffer;

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
            final MessageCodec<Message> codec = (MessageCodec<Message>) CodecResolver
                    .getCodec(message.getClass());
            
            if (codec == null)
            {
                throw new NullPointerException("codec");
            }
            
            DataBuffer buf = new DynamicDataBuffer();
            buf.writeByte(codec.getOpcode());
            codec.encode(buf, message);
            buf.copyWritedBytesToReadableBytes();
            
            return ChannelBuffers.copiedBuffer(buf.getReadableBytes());
        }
        
        return null;
    }
    
}
