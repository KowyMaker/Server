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

import java.io.StringReader;
import java.nio.charset.Charset;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneDecoder;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import com.kowymaker.server.core.net.codec.CodecResolver;
import com.kowymaker.server.core.net.codec.MessageCodec;
import com.kowymaker.server.core.net.handlers.MessageHandler;
import com.kowymaker.server.core.net.msg.Message;

public class ServerDecoder extends OneToOneDecoder
{
    
    @Override
    protected Object decode(ChannelHandlerContext ctx, Channel channel,
            Object msg) throws Exception
    {
        if (msg instanceof ChannelBuffer)
        {
            final ChannelBuffer buf = (ChannelBuffer) msg;
            final String message = buf.toString(Charset.defaultCharset());
            final SAXBuilder sxb = new SAXBuilder();
            final Document document = sxb.build(new StringReader(message));
            final Element root = document.getRootElement();
            final String opcode = root.getName();
            final MessageCodec<? extends Message, ? extends MessageHandler<? extends Message>> codec = CodecResolver
                    .getCodec(opcode);
            
            if (codec == null)
            {
                System.err.println("Error. Unrecognized opcode '" + opcode
                        + "' : " + message);
                return null;
            }
            
            return codec.decode(ctx, channel, root);
        }
        
        return msg;
    }
    
}
