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
