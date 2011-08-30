package com.kowymaker.server.data;

import java.util.ArrayList;
import java.util.List;

public class DataManager
{
    private static List<Class<?>> classes = new ArrayList<Class<?>>();
    
    static
    {
        
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
