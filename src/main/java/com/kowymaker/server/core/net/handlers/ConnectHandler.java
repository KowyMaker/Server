package com.kowymaker.server.core.net.handlers;

import java.util.List;

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
        final Player player = new Player(e.getChannel());
        player.setName(msg.getName());
        
        List<com.kowymaker.server.data.classes.Player> datas = server.getMain().getDatabase().query(com.kowymaker.server.data.classes.Player.class).where().eq("name", msg.getName()).findList();
        if(datas.isEmpty())
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
