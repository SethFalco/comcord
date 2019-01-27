package com.elypia.jdac.building;

import com.elypia.commandler.annotations.Compatible;
import com.elypia.jdac.IJDACBuilder;
import com.elypia.jdac.JDACEvent;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;

@Compatible(MessageEmbed.class)
public class MessageEmbedBuilder implements IJDACBuilder<MessageEmbed> {

    @Override
    public Message buildEmbed(JDACEvent event, MessageEmbed output) {
        return new MessageBuilder(output).build();
    }

    @Override
    public Message build(JDACEvent event, MessageEmbed output) {
        return null;
    }
}
