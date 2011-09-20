package com.kowymaker.server.core.net.msg;

public class ConnectMessage extends Message
{
    private String name;
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
}
