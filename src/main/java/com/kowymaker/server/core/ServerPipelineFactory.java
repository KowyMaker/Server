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

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;

import com.kowymaker.server.core.net.ServerDecoder;
import com.kowymaker.server.core.net.ServerEncoder;
import com.kowymaker.spec.net.CodecFrameDecoder;

public class ServerPipelineFactory implements ChannelPipelineFactory
{
    private final Server server;
    
    public ServerPipelineFactory(Server server)
    {
        this.server = server;
    }
    
    public ChannelPipeline getPipeline() throws Exception
    {
        final ChannelPipeline pipeline = Channels.pipeline();
        
        pipeline.addLast("framer", new CodecFrameDecoder());
        
        pipeline.addLast("decoder", new ServerDecoder(server));
        pipeline.addLast("encoder", new ServerEncoder(server));
        
        pipeline.addLast("handler", new ServerHandler(server));
        
        return pipeline;
    }
    
}
