package com.kowymaker.server.game.map;

import com.kowymaker.server.game.Game;

public class Maps
{
    private final Game game;
    
    private Map        map;
    
    public Maps(Game game)
    {
        this.game = game;
    }
    
    public Map getMap()
    {
        return map;
    }
    
    public void setMap(Map map)
    {
        this.map = map;
    }
    
    public Game getGame()
    {
        return game;
    }
}
