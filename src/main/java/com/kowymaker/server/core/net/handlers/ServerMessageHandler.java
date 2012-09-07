package com.kowymaker.server.core.net.handlers;

import com.kowymaker.server.core.Server;
import com.kowymaker.spec.net.MessageHandler;

public abstract class ServerMessageHandler<T> extends
        MessageHandler<T>
{
    protected final Server server;
    
    public ServerMessageHandler(Server server)
    {
        this.server = server;
    }
}
