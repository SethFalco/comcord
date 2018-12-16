package com.elypia.jdac.alias;

import com.elypia.commandler.impl.CommandEvent;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.GenericMessageEvent;

public class JDACEvent extends CommandEvent<JDA, GenericMessageEvent, Message> {

    public JDACEvent(CommandEvent<JDA, GenericMessageEvent, Message> event) {
        super(event.getCommandler(), event.getSource(), event.getInput());
    }

    @Override
    public <T> Message send(T output) {
        if (output instanceof String)
            return send((String)output);

        Message message = super.send(output);
        source.getChannel().sendMessage(message).queue();
        return message;
    }
}
