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
package com.kowymaker.server.core.net.handlers;

import java.util.List;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;

import com.kowymaker.server.core.Server;
import com.kowymaker.server.game.players.Player;
import com.kowymaker.spec.proto.NetworkCodecs;

public class ConnectHandler extends ServerMessageHandler<NetworkCodecs.ConnectMessage>
{
    public ConnectHandler(Server server)
    {
        super(server);
    }
    
    @Override
    public boolean handle(ChannelHandlerContext ctx, MessageEvent e,
            NetworkCodecs.ConnectMessage msg)
    {
        final Player player = new Player(e.getChannel());
        player.setName(msg.getName());
        
        final List<com.kowymaker.server.data.classes.Player> datas = server
                .getMain().getDatabase()
                .query(com.kowymaker.server.data.classes.Player.class).where()
                .eq("name", msg.getName()).findList();
        if (datas.isEmpty())
        {
            server.getMain().getGame().getMaps().getMap().spawn(player);
        }
        else
        {
            player.merge(datas.get(0));
        }
        
        server.getMain().getGame().getPlayers().add(player);
        
        System.out.println(msg.getName() + " is connected.");
        
        return true;
    }
    
}
