package com.elypia.jdac.building;

import com.elypia.commandler.annotations.Compatible;
import com.elypia.jdac.IJDACBuilder;
import com.elypia.jdac.JDACEvent;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.GenericMessageEvent;

@Compatible(EmbedBuilder.class)
public class EmbedBuilderBuilder implements IJDACBuilder<EmbedBuilder> {

    @Override
    public Message build(JDACEvent event, EmbedBuilder output) {
        GenericMessageEvent source = event.getSource();

        if (source.getChannelType().isGuild())
            output.setColor(source.getGuild().getSelfMember().getColor());

        return new MessageBuilder(output.build()).build();
    }

    @Override
    public Message buildEmbed(JDACEvent event, EmbedBuilder output) {
        return null;
    }
}
