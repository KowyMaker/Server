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
package com.kowymaker.server.game.map;

import java.util.ArrayList;
import java.util.List;

import com.google.protobuf.MessageLite;
import com.kowymaker.server.game.players.Player;
import com.kowymaker.server.utils.Location;

public class Map
{
    private Location           location = new Location(0, 0);
    private final List<Player> players  = new ArrayList<Player>();
    private final int          width;
    private final int          height;
    private final Case[][]     cases;
    
    public Map(int width, int height)
    {
        this.width = width;
        this.height = height;
        cases = new Case[width][height];
    }
    
    public Location getLocation()
    {
        return location;
    }
    
    public void setLocation(Location location)
    {
        this.location = location;
    }
    
    public List<Player> getPlayers()
    {
        return players;
    }
    
    public int getWidth()
    {
        return width;
    }
    
    public int getHeight()
    {
        return height;
    }
    
    public Case[][] getCases()
    {
        return cases;
    }
    
    public Case getCase(int x, int y)
    {
        if (Location.verfifyCoords(x, y))
        {
            return cases[x][y];
        }
        
        return null;
    }
    
    public void sendMessage(MessageLite msg)
    {
        for (final Player player : players)
        {
            player.sendMessage(msg);
        }
    }
    
    public void sendMessage(String messsage)
    {
        for (final Player player : players)
        {
            player.sendMessage(messsage);
        }
    }
    
    public void spawn(Player player)
    {
        player.getLocation().set(1, 1);
        player.setMap(this);
    }
}
