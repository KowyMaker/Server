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
    
    public String getJoinedString(int start)
    {
        return getJoinedString(start, args.length - 1);
    }
    
    public String getJoinedString(int start, int end)
    {
        final StringBuffer sb = new StringBuffer();
        sb.append(args[start]);
        
        if (end < 0)
        {
            end = args.length - end - 1;
        }
        
        if (end - start > 0)
        {
            for (int i = start + 1; i <= end; i++)
            {
                sb.append(' ');
                sb.append(args[i]);
            }
        }
        
        return sb.toString();
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
        boolean value = false;
        
        if (index < args.length && index >= 0)
        {
            final String str = args[index];
            if (str.equalsIgnoreCase("true") || str.equalsIgnoreCase("yes")
                    || str.equalsIgnoreCase("1"))
            {
                value = true;
            }
        }
        
        return value;
    }
    
    public int argsLength()
    {
        return args.length;
    }
}
