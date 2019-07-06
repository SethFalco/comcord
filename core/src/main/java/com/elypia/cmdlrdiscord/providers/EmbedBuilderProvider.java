package com.elypia.cmdlrdiscord.providers;

import com.elypia.cmdlrdiscord.interfaces.DiscordProvider;
import com.elypia.commandler.CommandlerEvent;
import com.elypia.commandler.annotations.Provider;
import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.GenericMessageEvent;

@Provider(provides = Message.class, value = EmbedBuilder.class)
public class EmbedBuilderProvider implements DiscordProvider<EmbedBuilder> {

    @Override
    public Message buildMessage(CommandlerEvent<?> event, EmbedBuilder output) {
        GenericMessageEvent source = (GenericMessageEvent)event.getSource();

        if (source.getChannelType().isGuild())
            output.setColor(source.getGuild().getSelfMember().getColor());

        return new MessageBuilder(output.build()).build();
    }
}
