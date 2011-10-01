/**
 * This file is part of Kowy Maker.
 * 
 * Kowy Maker is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * Kowy Maker is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * Kowy Maker. If not, see <http://www.gnu.org/licenses/>.
 */
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
