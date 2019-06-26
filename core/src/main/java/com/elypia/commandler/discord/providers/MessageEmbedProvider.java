package com.elypia.commandler.discord.providers;

import com.elypia.commandler.CommandlerEvent;
import com.elypia.commandler.annotations.Provider;
import com.elypia.commandler.discord.interfaces.DiscordProvider;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.*;

@Provider(provides = Message.class, value = MessageEmbed.class)
public class MessageEmbedProvider implements DiscordProvider<MessageEmbed> {

    @Override
    public Message buildMessage(CommandlerEvent<?> event, MessageEmbed output) {
        return new MessageBuilder(output).build();
    }
}
