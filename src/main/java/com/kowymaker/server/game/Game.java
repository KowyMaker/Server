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
package com.kowymaker.server.game;

import java.io.File;

import com.kowymaker.server.KowyMakerServer;
import com.kowymaker.server.data.sources.DataSource;
import com.kowymaker.server.game.map.Maps;
import com.kowymaker.server.game.players.Players;

public class Game implements DataSource
{
    private final KowyMakerServer main;
    private final File            gameDir = new File("game");
    
    private final Players         players;
    private final Maps            maps;
    
    public Game(KowyMakerServer main)
    {
        this.main = main;
        players = new Players(this);
        maps = new Maps(this);
    }
    
    public KowyMakerServer getMain()
    {
        return main;
    }
    
    public Players getPlayers()
    {
        return players;
    }
    
    public Maps getMaps()
    {
        return maps;
    }
    
    public File getGameDir()
    {
        return gameDir;
    }
    
    public void init()
    {
        if (!gameDir.exists())
        {
            gameDir.mkdirs();
        }
    }
    
    public void loadDatabase()
    {
        
    }
}
