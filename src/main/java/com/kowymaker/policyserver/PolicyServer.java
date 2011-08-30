package com.kowymaker.policyserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

public class PolicyServer extends Thread
{
    private final Logger       logger         = Logger.getLogger(PolicyServer.class
                                                      .getName());
    
    public static final String POLICY_REQUEST = "<policy-file-request/>";
    public static final String POLICY_XML     = "<?xml version=\"1.0\"?>"
                                                      + "<cross-domain-policy>"
                                                      + "<allow-access-from domain=\"*\" to-ports=\"*\" />"
                                                      + "</cross-domain-policy>";
    
    private final int          port;
    private ServerSocket       server;
    private boolean            listening;
    
    public PolicyServer(int port)
    {
        this.port = port;
    }
    
    @Override
    public void run()
    {
        logger.info("Starting Policy server...");
        try
        {
            server = new ServerSocket(port);
            listening = true;
            logger.info("Policy server started. Listening at port " + port);
            
            while (listening)
            {
                final Socket socket = server.accept();
                final PolicyServerConnection connection = new PolicyServerConnection(
                        socket);
                connection.start();
            }
        }
        catch (final IOException e)
        {
            e.printStackTrace();
        }
    }
    
    public boolean isListening()
    {
        return listening;
    }
    
    public void setListening(boolean listening)
    {
        this.listening = listening;
    }
    
    public int getPort()
    {
        return port;
    }
    
    @Override
    public void finalize()
    {
        try
        {
            server.close();
            listening = false;
        }
        catch (final IOException e)
        {
            e.printStackTrace();
        }
    }
}
