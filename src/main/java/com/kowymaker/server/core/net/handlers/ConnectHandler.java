package com.kowymaker.server.core.net.handlers;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;

import com.kowymaker.server.core.Server;
import com.kowymaker.server.core.net.msg.ConnectMessage;
import com.kowymaker.server.game.players.Player;

public class ConnectHandler extends MessageHandler<ConnectMessage>
{

    @Override
    public boolean handle(Server server, ChannelHandlerContext ctx,
            MessageEvent e, ConnectMessage msg) throws Exception
    {
        Player player = new Player(e.getChannel());
        player.setName(msg.getName());
        server.getMain().getGame().getPlayers().add(player);
        System.out.println(msg.getName() + " is connected.");
        return true;
    }
    
}
