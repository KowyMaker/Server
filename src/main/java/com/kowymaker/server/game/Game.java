package com.kowymaker.server.game;

import java.io.File;
import java.util.List;

import com.kowymaker.server.KowyMakerServer;
import com.kowymaker.server.data.Database;
import com.kowymaker.server.data.classes.Player;
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
    
    @Override
    public void loadDatabase()
    {
        
    }
}
