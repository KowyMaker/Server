package com.kowymaker.server.console.commands;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CommandResolver
{
    private static Map<String, Command> commands = new HashMap<String, Command>();
    
    static
    {
        try
        {
            bind(StopCommand.class);
            bind(StressTestCommand.class);
            bind(UtilsCommand.class);
        }
        catch (final Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public static <T extends Command> void bind(Class<T> clazz)
            throws Exception
    {
        final Constructor<T> constructor = clazz.getDeclaredConstructor();
        final T command = constructor.newInstance();
        for (final String code : command.getCommands())
        {
            commands.put(code, command);
        }
    }
    
    @SuppressWarnings("unchecked")
    public static <T extends Command> T getCommand(String command)
    {
        return (T) commands.get(command);
    }
    
    public static Set<String> getCommands()
    {
        return commands.keySet();
    }
}
