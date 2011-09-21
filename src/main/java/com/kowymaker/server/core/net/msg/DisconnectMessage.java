package com.kowymaker.server.core.net.msg;

import com.kowymaker.server.game.players.Player;

public class DisconnectMessage extends Message
{
    private String player;
    
    public String getPlayer()
    {
        return player;
    }
    
    public void setPlayer(String player)
    {
        this.player = player;
    }
    
    public void setPlayer(Player player)
    {
        this.player = player.getName();
    }
}
