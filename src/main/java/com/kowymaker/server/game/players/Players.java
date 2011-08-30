package com.kowymaker.server.game.players;

import java.util.*;

import com.kowymaker.server.game.Game;

public class Players
{
    private final Game          game;
    private Map<String, Player> players = new HashMap<String, Player>();
    
    public Players(Game game)
    {
        this.game = game;
    }
    
    public void add(Player player)
    {
        players.put(player.getName(), player);
    }
    
    public Player get(String name)
    {
        return players.get(name);
    }
}
