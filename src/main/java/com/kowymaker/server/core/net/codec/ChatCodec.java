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
