package com.kowymaker.server.commands;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import com.kowymaker.server.KowyMakerServer;
import com.kowymaker.server.annotations.Command;
import com.kowymaker.server.interfaces.CommandSender;

public class CommandsManager
{
    private final KowyMakerServer     main;
    
    private final Map<String, Method> aliases = new HashMap<String, Method>();
    private final Map<String, String> descs   = new HashMap<String, String>();
    
    public CommandsManager(KowyMakerServer main)
    {
        this.main = main;
        register(BasicCommands.class);
    }
    
    public void register(Class<?> clazz)
    {
        for (Method method : clazz.getMethods())
        {
            if (Modifier.isStatic(method.getModifiers())
                    && (method.getReturnType() == Boolean.class || method
                            .getReturnType() == boolean.class))
            {
                if (method.isAnnotationPresent(Command.class))
                {
                    Command command = method.getAnnotation(Command.class);
                    for (String alias : command.aliases())
                    {
                        System.out.println("Register command " + alias + " ("
                                + method.getName() + ")");
                        aliases.put(alias, method);
                        descs.put(alias, command.desc());
                    }
                }
            }
        }
    }
    
    public boolean execute(CommandSender sender, String command)
    {
        if (command.startsWith("/"))
        {
            command = command.substring(1);
        }
        
        String[] args = command.split(" ");
        CommandContext context = new CommandContext(args);
        Method method = aliases.get(context.getName());
        if (method != null)
        {
            if (method.isAnnotationPresent(Command.class))
            {
                Command cmd = method.getAnnotation(Command.class);
                if (context.argsLength() >= cmd.min()
                        && (context.argsLength() <= cmd.max() || cmd.max() == -1))
                {
                    try
                    {
                        boolean result = (Boolean) method.invoke(null, main,
                                context, sender);
                        if (result)
                        {
                            return true;
                        }
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
                
                sender.sendMessage("Usage: " + cmd.usage().replace("<command>", context.getName()));
            }
        }
        return false;
    }
    
    public KowyMakerServer getMain()
    {
        return main;
    }
    
    public Map<String, Method> getAliases()
    {
        return aliases;
    }
    
    public Map<String, String> getDescs()
    {
        return descs;
    }
}
