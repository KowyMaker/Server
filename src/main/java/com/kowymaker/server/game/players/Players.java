package com.kowymaker.server.game.players;

import java.util.HashMap;
import java.util.Map;

import org.jboss.netty.channel.Channel;

import com.kowymaker.server.game.Game;

public class Players
{
    private final Game                game;
    private final Map<String, Player> players = new HashMap<String, Player>();
    
    public Players(Game game)
    {
        this.game = game;
    }
    
    public void add(Player player)
    {
        players.put(player.getName(), player);
    }
    
    public void remove(Player player)
    {
        players.remove(player);
    }
    
    public Player get(String name)
    {
        return players.get(name);
    }
    
    public Player get(Channel channel)
    {
        Player p = null;
        
        for (final Player player : players.values())
        {
            if (player.getChannel().getId() == channel.getId())
            {
                p = player;
            }
        }
        
        return p;
    }
}
