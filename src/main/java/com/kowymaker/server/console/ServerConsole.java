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

import com.kowymaker.server.KowyMakerServer;
import com.kowymaker.server.interfaces.CommandSender;
import com.kowymaker.spec.console.ConsoleOutputManager;

public class ServerConsole implements CommandSender
{
    private final KowyMakerServer main;
    
    public ServerConsole(KowyMakerServer main)
    {
        this.main = main;
        // Setup Console Reader
        final ThreadCommandReader thread = new ThreadCommandReader(this);
        thread.setDaemon(true);
        thread.start();
        
        ConsoleOutputManager.register("server");
    }
    
    public boolean isRunning()
    {
        return main.isRunning();
    }
    
    public void handle(String commandCode)
    {
        main.getCommandsManager().execute(this, commandCode);
    }
    
    @Override
    public void sendMessage(String message)
    {
        System.out.println(message);
    }
}
