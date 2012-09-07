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

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;

import com.google.protobuf.Message;
import com.kowymaker.server.core.Server;
import com.kowymaker.spec.utils.data.DataBuffer;

public class ServerEncoder extends OneToOneEncoder
{
    private final Server server;
    
    public ServerEncoder(Server server)
    {
        super();
        this.server = server;
    }
    
    @Override
    protected Object encode(ChannelHandlerContext ctx, Channel channel,
            Object msg) throws Exception
    {
        if (msg instanceof Message)
        {
            final Message message = (Message) msg;
            
            int opcode = server.getCodec().getOpcode(message.getClass());
            
            if (opcode != -1)
            {
                DataBuffer data = new DataBuffer();
                data.writeByte(opcode);
                data.writeBytes(message.toByteArray());
                
                DataBuffer buf = new DataBuffer();
                buf.writeInt(data.size());
                buf.writeBytes(data);
                
                return buf;
            }
        }
        
        return null;
    }
    
}
