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
package com.kowymaker.server.utils;

public class Location
{
    private int         x;
    private int         y;
    private int         z;
    private Orientation orientation;
    
    public Location()
    {
        this(0, 0);
    }
    
    public Location(int x, int y)
    {
        this(x, y, Orientation.NULL);
    }
    
    public Location(int x, int y, int z)
    {
        this(x, y, z, Orientation.NULL);
    }
    
    public Location(int x, int y, Orientation orientation)
    {
        this(x, y, 0, orientation);
    }
    
    public Location(int x, int y, int z, Orientation orientation)
    {
        this.x = x;
        this.y = y;
        this.orientation = orientation;
    }
    
    public void set(int x, int y)
    {
        set(x, y, z);
    }
    
    public void set(int x, int y, int z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public int getX()
    {
        return x;
    }
    
    public void setX(int x)
    {
        this.x = x;
    }
    
    public int getY()
    {
        return y;
    }
    
    public void setY(int y)
    {
        this.y = y;
    }
    
    public int getZ()
    {
        return z;
    }
    
    public void setZ(int z)
    {
        this.z = z;
    }
    
    public Orientation getOrientation()
    {
        return orientation;
    }
    
    public void setOrientation(Orientation orientation)
    {
        this.orientation = orientation;
    }
    
    public Location getRelative(int xOffset, int yOffset)
    {
        return getRelative(xOffset, yOffset, 0);
    }
    
    public Location getRelative(int xOffset, int yOffset, int zOffset)
    {
        return new Location(x + xOffset, y + yOffset, z + zOffset);
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + (orientation == null ? 0 : orientation.hashCode());
        result = prime * result + x;
        result = prime * result + y;
        result = prime * result + z;
        return result;
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
        if (!(obj instanceof Location))
        {
            return false;
        }
        final Location other = (Location) obj;
        if (orientation != other.orientation)
        {
            return false;
        }
        if (x != other.x)
        {
            return false;
        }
        if (y != other.y)
        {
            return false;
        }
        if (z != other.z)
        {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString()
    {
        final StringBuilder builder = new StringBuilder();
        builder.append("Location [x=");
        builder.append(x);
        builder.append(", y=");
        builder.append(y);
        builder.append(", z=");
        builder.append(z);
        builder.append(", orientation=");
        builder.append(orientation);
        builder.append("]");
        return builder.toString();
    }
    
    public static boolean verfifyCoords(int x, int y)
    {
        if ((x + y) % 2 == 0)
        {
            return true;
        }
        return false;
    }
    
    public static enum Orientation
    {
        NORTH(0, -2), SOUTH(0, 2), EAST(2, 0), WEST(-2, 0), NORTHWEST(-1, -1), NORTHEAST(
                1, -1), SOUTHWEST(-1, 1), SOUTHEAST(1, 1), NULL;
        
        private final int xOffset;
        private final int yOffset;
        
        Orientation()
        {
            this(0, 0);
        }
        
        Orientation(int xOffset, int yOffset)
        {
            this.xOffset = xOffset;
            this.yOffset = yOffset;
        }
        
        public int getXOffset()
        {
            return xOffset;
        }
        
        public int getYOffset()
        {
            return yOffset;
        }
    }
}
