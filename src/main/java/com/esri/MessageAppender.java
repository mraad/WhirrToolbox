package com.esri;

import com.esri.arcgis.geodatabase.IGPMessages;
import org.apache.log4j.WriterAppender;
import org.apache.log4j.spi.LoggingEvent;

import java.io.IOException;

/**
 */
public class MessageAppender extends WriterAppender
{
    private IGPMessages m_messages;

    public MessageAppender(final IGPMessages messages)
    {
        m_messages = messages;
    }

    @Override
    public void append(final LoggingEvent event)
    {
        try
        {
            m_messages.addMessage(this.layout.format(event));
        }
        catch (IOException e)
        {
            // Ignore
        }
    }

    @Override
    public boolean requiresLayout()
    {
        return true;
    }
}
