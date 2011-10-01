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

import com.kowymaker.server.game.Game;
import com.kowymaker.server.utils.Location;

public class Case
{
    private final Game game;
    private final Map  map;
    private Location   location;
    
    private List<Tile> tiles = new ArrayList<Tile>();
    
    public Case(Game game, Map map, int x, int y)
    {
        this(game, map, new Location(x, y));
    }
    
    public Case(Game game, Map map, Location location)
    {
        this.game = game;
        this.map = map;
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
    
    public Game getGame()
    {
        return game;
    }
    
    public Map getMap()
    {
        return map;
    }
    
    public List<Tile> getTiles()
    {
        return tiles;
    }
    
    public void setTiles(List<Tile> tiles)
    {
        this.tiles = tiles;
    }
    
    public Tile getTile(int z)
    {
        return tiles.get(z);
    }
}
