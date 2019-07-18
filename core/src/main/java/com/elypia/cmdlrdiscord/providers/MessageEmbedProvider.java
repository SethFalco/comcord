package com.elypia.cmdlrdiscord.providers;

import com.elypia.cmdlrdiscord.interfaces.DiscordProvider;
import com.elypia.commandler.CommandlerEvent;
import com.elypia.commandler.annotations.Provider;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.*;

@Provider(provides = Message.class, value = MessageEmbed.class)
public class MessageEmbedProvider implements DiscordProvider<MessageEmbed> {

    @Override
    public Message buildMessage(CommandlerEvent<?, ?> event, MessageEmbed output) {
        return new MessageBuilder(output).build();
    }
}
