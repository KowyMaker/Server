package com.kowymaker.server.game.players;

import org.jboss.netty.channel.Channel;

public class Player
{
    private final Channel channel;
    private String        name = null;
    
    public Player(Channel channel)
    {
        this.channel = channel;
    }
    
    public Channel getChannel()
    {
        return channel;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
}
