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
package com.kowymaker.server.core;

import java.util.logging.Logger;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;

import com.kowymaker.server.core.net.codec.CodecResolver;
import com.kowymaker.server.core.net.handlers.MessageHandler;
import com.kowymaker.server.core.net.msg.Message;
import com.kowymaker.server.core.tasks.Task;
import com.kowymaker.server.game.players.Player;

public class ServerHandler extends SimpleChannelUpstreamHandler
{
    private final Server server;
    @SuppressWarnings("unused")
    private final Logger logger = Logger.getLogger(ServerHandler.class
                                        .getName());
    
    public ServerHandler(Server server)
    {
        this.server = server;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
            throws Exception
    {
        if (!(e.getMessage() instanceof Message))
        {
            super.messageReceived(ctx, e);
        }
        
        final Message msg = (Message) e.getMessage();
        final MessageHandler<Message> handler = (MessageHandler<Message>) CodecResolver
                .getHandler(msg.getClass());
        
        final Task task = new Task(server, ctx, e, msg, handler);
        server.getTasks().add(task);
    }
    
    @Override
    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e)
            throws Exception
    {
        server.getChannels().add(e.getChannel());
    }
    
    @Override
    public void channelDisconnected(ChannelHandlerContext ctx,
            ChannelStateEvent e) throws Exception
    {
        server.getChannels().remove(e.getChannel());
        
        final Player attached = server.getMain().getGame().getPlayers()
                .get(e.getChannel());
        
        if (attached != null)
        {
            server.getMain().getGame().getPlayers().remove(attached);
        }
    }
    
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e)
            throws Exception
    {
        if (this == ctx.getPipeline().getLast())
        {
            e.getCause().printStackTrace();
        }
    }
}
