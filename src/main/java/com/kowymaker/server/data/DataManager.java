package com.kowymaker.server.data;

import java.util.ArrayList;
import java.util.List;

import com.kowymaker.server.data.classes.Player;

public class DataManager
{
    private static List<Class<?>> classes = new ArrayList<Class<?>>();
    
    static
    {
        bind(Player.class);
    }
    
    private static void bind(Class<?> clazz)
    {
        classes.add(clazz);
    }
    
    public static void registerClasses(List<Class<?>> list)
    {
        list.addAll(classes);
    }
}
