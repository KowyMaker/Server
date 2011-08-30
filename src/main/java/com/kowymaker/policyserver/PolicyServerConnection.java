package com.kowymaker.policyserver;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class PolicyServerConnection extends Thread
{
    private final Socket   socket;
    private BufferedReader socketIn;
    private PrintWriter    socketOut;
    
    public PolicyServerConnection(Socket socket)
    {
        this.socket = socket;
    }
    
    @Override
    public void run()
    {
        try
        {
            socketIn = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));
            socketOut = new PrintWriter(socket.getOutputStream(), true);
            readPolicyRequest();
        }
        catch (final Exception e)
        {
            e.printStackTrace();
        }
    }
    
    protected void readPolicyRequest()
    {
        try
        {
            final String request = read();
            
            if (request.equals(PolicyServer.POLICY_REQUEST))
            {
                writePolicy();
            }
        }
        catch (final Exception e)
        {
            e.printStackTrace();
        }
        finalize();
    }
    
    protected void writePolicy()
    {
        try
        {
            socketOut.write(PolicyServer.POLICY_XML + "\u0000");
            socketOut.close();
        }
        catch (final Exception e)
        {
            e.printStackTrace();
        }
    }
    
    protected String read()
    {
        final StringBuffer buffer = new StringBuffer();
        int codePoint;
        boolean zeroByteRead = false;
        
        try
        {
            do
            {
                codePoint = socketIn.read();
                
                if (codePoint == 0)
                {
                    zeroByteRead = true;
                }
                else if (Character.isValidCodePoint(codePoint))
                {
                    buffer.appendCodePoint(codePoint);
                }
            } while (!zeroByteRead && buffer.length() < 200);
        }
        catch (final Exception e)
        {
            e.printStackTrace();
        }
        
        return buffer.toString();
    }
    
    @Override
    protected void finalize()
    {
        try
        {
            socketIn.close();
            socketOut.close();
            socket.close();
        }
        catch (final Exception e)
        {
            e.printStackTrace();
        }
    }
}
