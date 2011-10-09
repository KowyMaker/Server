package com.kowymaker.server.core.net.handlers;

import com.kowymaker.server.core.Server;
import com.kowymaker.spec.net.MessageHandler;
import com.kowymaker.spec.net.msg.Message;

public abstract class ServerMessageHandler<T extends Message> extends MessageHandler<T>
{
    protected Server server;
    
    public void init()
    {
        server = (Server) properties.get("server");
    }
}
