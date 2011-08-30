package com.kowymaker.server.console;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class LogFormatter extends Formatter
{
    
    @Override
    public String format(LogRecord record)
    {
        final StringBuffer sb = new StringBuffer();
        
        sb.append(new SimpleDateFormat("HH:mm:ss").format(new Date(record
                .getMillis())));
        sb.append(' ');
        sb.append('[');
        sb.append(record.getLevel().toString());
        sb.append("] ");
        sb.append(record.getMessage());
        sb.append('\n');
        
        return sb.toString();
    }
    
}
