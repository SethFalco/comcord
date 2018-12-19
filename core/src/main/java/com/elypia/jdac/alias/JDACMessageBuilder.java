package com.elypia.jdac.alias;

import com.elypia.commandler.MessageBuilder;
import com.elypia.commandler.interfaces.ICommandEvent;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.message.GenericMessageEvent;

import java.util.Objects;

public class JDACMessageBuilder extends MessageBuilder<Message> {

    @Override
    public Message build(ICommandEvent event, Object object) {
        Objects.requireNonNull(object);
        IJDACBuilder builder = (IJDACBuilder)getBuilder(object.getClass());
        JDACEvent jdacEvent = (JDACEvent)event;
        GenericMessageEvent source = jdacEvent.getSource();

        if (!source.getChannelType().isGuild())
            return builder.buildEmbed((JDACEvent)event, object);

        else {
            TextChannel channel = source.getTextChannel();

            if (source.getGuild().getSelfMember().hasPermission(channel, Permission.MESSAGE_EMBED_LINKS)) {
                Message message =  builder.buildEmbed((JDACEvent) event, object);

                if (message != null)
                    return message;
            }
        }

        return (Message)builder.build(event, object);
    }
}
