package com.kowymaker.server.commands;

public class CommandContext
{
    private final String   name;
    private final String[] args;
    
    public CommandContext(String[] args)
    {
        name = args[0];
        this.args = new String[args.length - 1];
        if (args.length > 1)
        {
            System.arraycopy(args, 1, this.args, 0, args.length - 1);
        }
    }
    
    public String getName()
    {
        return name;
    }
    
    public String getString(int index)
    {
        
        if (index < args.length)
        {
            return args[index];
        }
        
        return null;
    }
    
    public int getInteger(int index)
    {
        if (index < args.length && index >= 0)
        {
            return Integer.parseInt(args[index]);
        }
        
        return 0;
    }
    
    public boolean getBoolean(int index)
    {
        if(index < args.length && index >= 0)
        {
            return Boolean.parseBoolean(args[index]);
        }
        
        return false;
    }
    
    public int argsLength()
    {
        return args.length;
    }
}
