package com.elypia.commandler.discord.providers;

import com.elypia.commandler.CommandlerEvent;
import com.elypia.commandler.annotations.Provider;
import com.elypia.commandler.discord.interfaces.DiscordProvider;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.internal.entities.DataMessage;

@Provider(provides = Message.class, value = {Message.class, DataMessage.class})
public class MessageProvider implements DiscordProvider<Message> {

    @Override
    public Message buildMessage(CommandlerEvent<?> event, Message output) {
        return output;
    }
}
