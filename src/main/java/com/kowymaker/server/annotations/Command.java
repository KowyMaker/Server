package com.kowymaker.server.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = {ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Command
{
    String[] aliases();
    
    String desc() default "";
    
    int min() default 0;
    
    int max() default -1;
    
    String usage() default "/<command>";
}
