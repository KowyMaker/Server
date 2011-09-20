package com.kowymaker.server.game.map;

import com.kowymaker.server.utils.Location;

public class Tile
{
    private final Case parent;
    private Location   location;
    
    public Tile(Case parent, int x, int y, int z)
    {
        this(parent, new Location(x, y, z));
    }
    
    public Tile(Case parent, Location location)
    {
        this.parent = parent;
        this.location = location;
    }
    
    public Location getLocation()
    {
        return location;
    }
    
    public void setLocation(Location location)
    {
        this.location = location;
    }
    
    public Case getParent()
    {
        return parent;
    }
}
