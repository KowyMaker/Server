package com.kowymaker.server.core.net.handlers;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;

import com.kowymaker.server.core.Server;
import com.kowymaker.server.core.net.msg.ChatMessage;
import com.kowymaker.server.game.players.Player;

public class ChatHandler extends MessageHandler<ChatMessage>
{
    
    @Override
    public boolean handle(Server server, ChannelHandlerContext ctx,
            MessageEvent e, ChatMessage msg) throws Exception
    {
        final String message = msg.getMessage();
        final Player player = server.getMain().getGame().getPlayers()
                .get(e.getChannel());
        if (message.startsWith("/"))
        {
            server.getMain().getCommandsManager().execute(player, message);
        }
        else
        {
            for (final Player p : player.getMap().getPlayers())
            {
                if (!p.equals(player))
                {
                    p.sendMessage(msg);
                }
            }
        }
        return true;
    }
    
}
