package com.kowymaker.server.console;

import java.util.logging.ConsoleHandler;

public class TerminalConsoleHandler extends ConsoleHandler
{
    @SuppressWarnings("unused")
    private final ServerConsole console;
    
    public TerminalConsoleHandler(ServerConsole console)
    {
        super();
        this.console = console;
        setOutputStream(System.out);
        setFormatter(new LogFormatter());
    }
    
    @Override
    public synchronized void flush()
    {
        super.flush();
    }
}
