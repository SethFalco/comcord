package com.elypia.cmdlrdiscord.providers;

import com.elypia.cmdlrdiscord.interfaces.DiscordProvider;
import com.elypia.commandler.CommandlerEvent;
import com.elypia.commandler.annotations.Provider;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.internal.entities.DataMessage;

// TODO: If the object returned is already the Controller type, just return that.
@Provider(provides = Message.class, value = {Message.class, DataMessage.class})
public class MessageProvider implements DiscordProvider<Message> {

    @Override
    public Message buildMessage(CommandlerEvent<?, ?> event, Message output) {
        return output;
    }
}
