package com.kowymaker.server.commands;

/**
 * Determine all commands parameters (Command name, command arguments, etc...)
 * 
 * @author Koka El Kiwi
 * 
 */
public class CommandContext
{
    private final String   name;
    private final String[] args;
    
    /**
     * Constructor of command context, based on command splitted by ' ' char.
     * 
     * @param args
     *            Command splitted by ' ' char.
     */
    public CommandContext(String[] args)
    {
        name = args[0];
        this.args = new String[args.length - 1];
        if (args.length > 1)
        {
            System.arraycopy(args, 1, this.args, 0, args.length - 1);
        }
    }
    
    /**
     * Get command name.
     * @return Command name
     */
    public String getName()
    {
        return name;
    }
    
    /**
     * Get String argument based on index.
     * @param index Index of argument (start is 0)
     * @return String argument
     */
    public String getString(int index)
    {
        
        if (index < args.length)
        {
            return args[index];
        }
        
        return null;
    }
    
    /**
     * Get a joined String based on all args that's starting at a specified index.
     * @param start Index of start argument.
     * @return Joined String arguments.
     */
    public String getJoinedString(int start)
    {
        return getJoinedString(start, args.length - 1);
    }
    
    /**
     * Get a joined String based on all args that's starting at a specified index and ending at an other index.
     * @param start Index of start argument.
     * @param end End of start argument.
     * @return Joined String arguments.
     */
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
    
    /**
     * Get an Integer argument at specified index.
     * @param index Argument index
     * @return Integer argument. 0 if the index is not in range.
     */
    public int getInteger(int index)
    {
        if (index < args.length && index >= 0)
        {
            return Integer.parseInt(args[index]);
        }
        
        return 0;
    }
    
    /**
     * Get a Boolean argument at specified index
     * @param index Argument index
     * @return Boolean argument. false is the index is not in range.
     */
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
    
    /**
     * Get number of arguments passed to this command.
     * @return Number of arguments passed to the command
     */
    public int argsLength()
    {
        return args.length;
    }
}
