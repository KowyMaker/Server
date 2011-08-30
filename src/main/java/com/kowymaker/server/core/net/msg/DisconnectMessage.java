package com.kowymaker.server.core.net.msg;

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
}
