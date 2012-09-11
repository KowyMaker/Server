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
package com.kowymaker.server.core.tasks;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;

import com.google.protobuf.MessageOrBuilder;
import com.kowymaker.server.core.Server;
import com.kowymaker.spec.net.MessageHandler;

public class Task
{
    private final Server                server;
    private final ChannelHandlerContext context;
    private final MessageEvent          event;
    private final MessageOrBuilder      message;
    private final MessageHandler<?>     handler;
    private boolean                     done = false;
    
    public Task(Server server, ChannelHandlerContext context,
            MessageEvent event, MessageOrBuilder message,
            MessageHandler<?> handler)
    {
        this.server = server;
        this.context = context;
        this.event = event;
        this.message = message;
        this.handler = handler;
    }
    
    public Server getServer()
    {
        return server;
    }
    
    public ChannelHandlerContext getContext()
    {
        return context;
    }
    
    public MessageEvent getEvent()
    {
        return event;
    }
    
    public MessageOrBuilder getMessage()
    {
        return message;
    }
    
    public MessageHandler<?> getHandler()
    {
        return handler;
    }
    
    public boolean done()
    {
        return done;
    }
    
    public void done(boolean done)
    {
        this.done = done;
    }
}
