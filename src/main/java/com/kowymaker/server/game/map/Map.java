package com.kowymaker.server.game.map;

import java.util.ArrayList;
import java.util.List;

import com.kowymaker.server.core.net.msg.Message;
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
    
    public void sendMessage(Message msg)
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
