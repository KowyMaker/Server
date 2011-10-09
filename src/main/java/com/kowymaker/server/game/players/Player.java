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
package com.kowymaker.server.game.players;

import org.jboss.netty.channel.Channel;

import com.kowymaker.server.data.Mergeable;
import com.kowymaker.server.game.map.Map;
import com.kowymaker.server.interfaces.CommandSender;
import com.kowymaker.server.utils.Location;
import com.kowymaker.spec.net.msg.Message;

public class Player implements CommandSender,
        Mergeable<com.kowymaker.server.data.classes.Player>
{
    private final Channel channel;
    private String        name     = null;
    private Location      location = new Location();
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
    
    public void setLocation(Location location)
    {
        this.location = location;
    }
    
    public void setMap(Map map)
    {
        this.map = map;
    }
    
    public void sendMessage(Message msg)
    {
        channel.write(msg);
    }
    
    @Override
    public void sendMessage(String message)
    {
//        final ChatMessage msg = new ChatMessage();
//        msg.setMessage(message);
//        
//        channel.write(msg);
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
    
    @Override
    public void merge(com.kowymaker.server.data.classes.Player data)
    {
        
    }
}
