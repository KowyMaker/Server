package com.kowymaker.server.core;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.handler.codec.frame.DelimiterBasedFrameDecoder;
import org.jboss.netty.handler.codec.frame.Delimiters;

import com.kowymaker.server.core.net.ServerDecoder;
import com.kowymaker.server.core.net.ServerEncoder;

public class ServerPipelineFactory implements ChannelPipelineFactory
{
    private final Server server;
    
    public ServerPipelineFactory(Server server)
    {
        this.server = server;
    }
    
    @Override
    public ChannelPipeline getPipeline() throws Exception
    {
        final ChannelPipeline pipeline = Channels.pipeline();
        
        pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192,
                Delimiters.nulDelimiter()));
        
        pipeline.addLast("decoder", new ServerDecoder());
        pipeline.addLast("encoder", new ServerEncoder());
        
        pipeline.addLast("handler", new ServerHandler(server));
        
        return pipeline;
    }
    
}
