package com.mcssoftware.irremote;

import java.util.ArrayList;
import java.util.Collections;

public class IRMessageRequest
{
    private final ArrayList<IRMessage> _messages = new ArrayList<>();

    public IRMessageRequest(IRMessage... args)
    {
        Collections.addAll(_messages, args);
    }

    public ArrayList<IRMessage> getMessages()
    {
        return _messages;
    }

    public long getRequestTime()
    {
        long time = 0;
        for(int a=0;a<_messages.size();++a)
        {
            time += _messages.get(a).getMessageTime();
        }
        return time;
    }
}
