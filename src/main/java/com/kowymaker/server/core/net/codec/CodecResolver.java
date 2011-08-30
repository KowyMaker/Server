package com.kowymaker.server.core.net.codec;

import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;

import com.kowymaker.server.core.net.handlers.MessageHandler;
import com.kowymaker.server.core.net.msg.Message;

public class CodecResolver
{
    private static Map<String, MessageCodec<? extends Message, ? extends MessageHandler<? extends Message>>> codecs   = new HashMap<String, MessageCodec<? extends Message, ? extends MessageHandler<? extends Message>>>();
    private static Map<String, MessageHandler<? extends Message>>                                            handlers = new HashMap<String, MessageHandler<? extends Message>>();
    private static Map<Class<? extends Message>, String>                                                     msg      = new HashMap<Class<? extends Message>, String>();
    
    static
    {
        try
        {
            bind(ConnectCodec.class);
            bind(DisconnectCodec.class);
        }
        catch (final Exception e)
        {
            e.printStackTrace();
        }
    }
    
    @SuppressWarnings("unchecked")
    public static <T extends Message, U extends MessageHandler<T>, V extends MessageCodec<T, U>> void bind(
            Class<V> clazz) throws Exception
    {
        // Codec
        final Constructor<V> constructor = clazz.getDeclaredConstructor();
        final V codec = constructor.newInstance();
        codecs.put(codec.getOpcode(), codec);
        final Class<T> msgClazz = (Class<T>) ((ParameterizedType) clazz
                .getGenericSuperclass()).getActualTypeArguments()[0];
        msg.put(msgClazz, codec.getOpcode());
        
        // Handler
        final Class<U> handlerClazz = (Class<U>) ((ParameterizedType) clazz
                .getGenericSuperclass()).getActualTypeArguments()[1];
        final Constructor<U> handlerConstructor = handlerClazz
                .getDeclaredConstructor();
        final U handler = handlerConstructor.newInstance();
        handlers.put(codec.getOpcode(), handler);
    }
    
    public static MessageCodec<? extends Message, ? extends MessageHandler<? extends Message>> getCodec(
            String opcode)
    {
        return codecs.get(opcode);
    }
    
    public static MessageCodec<? extends Message, ? extends MessageHandler<? extends Message>> getCodec(
            Class<? extends Message> clazz)
    {
        return codecs.get(msg.get(clazz));
    }
    
    public static MessageHandler<? extends Message> getHandler(String opcode)
    {
        return handlers.get(opcode);
    }
    
    public static MessageHandler<? extends Message> getHandler(
            Class<? extends Message> clazz)
    {
        return handlers.get(msg.get(clazz));
    }
}
