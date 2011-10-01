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
