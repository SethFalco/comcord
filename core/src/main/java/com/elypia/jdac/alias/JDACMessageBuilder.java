package com.elypia.jdac.alias;

import com.elypia.commandler.MessageBuilder;
import com.elypia.commandler.interfaces.ICommandEvent;
import com.elypia.jdac.JDACUtils;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.GenericMessageEvent;

import java.util.Objects;

public class JDACMessageBuilder extends MessageBuilder<Message> {

    @Override
    public Message build(ICommandEvent event, Object object) {
        Objects.requireNonNull(object);
        IJDACBuilder builder = (IJDACBuilder)getBuilder(object.getClass());
        JDACEvent jdacEvent = (JDACEvent)event;
        GenericMessageEvent source = jdacEvent.getSource();

        if (JDACUtils.canSendEmbeds(source)) {
            Message embed =  builder.buildEmbed((JDACEvent) event, object);

            if (embed != null)
                return embed;
        }

        return (Message)builder.build(event, object);
    }
}
