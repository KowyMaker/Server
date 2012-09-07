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
package com.kowymaker.server.core.net.handlers;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;

import com.kowymaker.server.core.Server;
import com.kowymaker.spec.proto.NetworkCodecs;

public class DisconnectHandler extends ServerMessageHandler<NetworkCodecs.DisconnectMessage>
{
    
    public DisconnectHandler(Server server)
    {
        super(server);
    }
    
    @Override
    public boolean handle(ChannelHandlerContext ctx, MessageEvent e,
            NetworkCodecs.DisconnectMessage msg)
    {
        server.getChannels().write(msg);
        return true;
    }
    
}
