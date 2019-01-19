package com.elypia.jdac.building;

import com.elypia.commandler.annotations.Compatible;
import com.elypia.jdac.alias.*;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.internal.entities.DataMessage;

@Compatible({Message.class, DataMessage.class})
public class DataMessageBuilder implements IJDACBuilder<Message> {

    @Override
    public Message build(JDACEvent event, Message output) {
        return output;
    }
}
