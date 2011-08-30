package com.kowymaker.server.console;

import java.io.IOException;

public class ThreadCommandReader extends Thread
{
    private final ServerConsole console;
    
    public ThreadCommandReader(ServerConsole console)
    {
        this.console = console;
    }
    
    @Override
    public void run()
    {
        String s = null;
        
        try
        {
            while (console.isRunning())
            {
                s = readLine();
                
                if (s != null)
                {
                    console.handle(s);
                }
            }
        }
        catch (final Exception e)
        {
            e.printStackTrace();
        }
    }
    
    private String readLine() throws IOException
    {
        if (System.in.available() > 0)
        {
            final StringBuffer buf = new StringBuffer();
            while (true)
            {
                final int i = System.in.read();
                
                if (i == -1 || i == 10 || i == 13)
                {
                    return buf.toString();
                }
                
                buf.append((char) i);
            }
        }
        return null;
    }
}
