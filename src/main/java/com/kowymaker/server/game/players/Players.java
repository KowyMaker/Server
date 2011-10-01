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
    
    public Map<String, Player> getPlayers()
    {
        return players;
    }
}
