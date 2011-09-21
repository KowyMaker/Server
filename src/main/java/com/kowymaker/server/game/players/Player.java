package com.kowymaker.server.game.players;

import org.jboss.netty.channel.Channel;

import com.kowymaker.server.core.net.msg.ChatMessage;
import com.kowymaker.server.core.net.msg.Message;
import com.kowymaker.server.game.map.Map;
import com.kowymaker.server.interfaces.CommandSender;
import com.kowymaker.server.utils.Location;

public class Player implements CommandSender
{
    private final Channel channel;
    private String        name = null;
    private Location      location;
    private Map           map;
    
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
    
    public Location getLocation()
    {
        return location;
    }
    
    public Map getMap()
    {
        return map;
    }
    
    public void sendMessage(Message msg)
    {
        channel.write(msg);
    }
    
    public void sendMessage(String message)
    {
        final ChatMessage msg = new ChatMessage();
        msg.setMessage(message);
        
        channel.write(msg);
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (!(obj instanceof Player))
        {
            return false;
        }
        final Player other = (Player) obj;
        if (channel == null)
        {
            if (other.channel != null)
            {
                return false;
            }
        }
        else if (!channel.getId().equals(other.getChannel().getId()))
        {
            return false;
        }
        if (name == null)
        {
            if (other.name != null)
            {
                return false;
            }
        }
        else if (!name.equals(other.name))
        {
            return false;
        }
        return true;
    }
}
