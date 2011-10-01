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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggerOutputStream extends ByteArrayOutputStream
{
    private final String separator = System.getProperty("line.separator");
    private final Logger logger;
    private final Level  level;
    
    public LoggerOutputStream(Logger logger, Level level)
    {
        super();
        this.logger = logger;
        this.level = level;
    }
    
    @Override
    public void flush() throws IOException
    {
        synchronized (this)
        {
            super.flush();
            final String record = this.toString();
            super.reset();
            
            if (record.length() > 0 && !record.equals(separator))
            {
                logger.logp(level, "", "", record);
            }
        }
    }
}
