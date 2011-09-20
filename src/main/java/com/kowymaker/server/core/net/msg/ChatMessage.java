package com.kowymaker.server.core.net.msg;

public class ChatMessage extends Message
{
    private String message;
    
    public String getMessage()
    {
        return message;
    }
    
    public void setMessage(String message)
    {
        this.message = message;
    }
}
