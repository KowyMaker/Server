package com.kowymaker.server.game;

import java.io.File;

import com.kowymaker.server.KowyMakerServer;
import com.kowymaker.server.game.players.Players;

public class Game
{
    private final KowyMakerServer main;
    private final File            gameDir = new File("game");
    
    private final Players         players;
    
    public Game(KowyMakerServer main)
    {
        this.main = main;
        players = new Players(this);
    }
    
    public KowyMakerServer getMain()
    {
        return main;
    }
    
    public Players getPlayers()
    {
        return players;
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
}
