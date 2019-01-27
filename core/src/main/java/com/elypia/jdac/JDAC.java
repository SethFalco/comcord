package com.elypia.jdac;

import com.elypia.commandler.Commandler;
import com.elypia.jdac.building.DefaultBuilder;
import com.elypia.jdac.building.EmbedBuilderBuilder;
import com.elypia.jdac.building.MessageEmbedBuilder;
import com.elypia.jdac.parsing.*;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.GenericMessageEvent;

public class JDAC extends Commandler<GenericMessageEvent, Message> {

    public JDAC(Builder jdacBuilder) {
        super(jdacBuilder);

        builder = new JDACResponseBuilder();
        processor = new JDACProcessor(this);

        parser.add(
            EmoteParser.class,
            GuildParser.class,
            RoleParser.class,
            TextChannelParser.class,
            UserParser.class,
            VoiceChannelParser.class
        );

        builder.add(
            DefaultBuilder.class,
            EmbedBuilderBuilder.class,
            MessageEmbedBuilder.class
        );
    }

    public JDACDispatcher getDispatcher() {
        return new JDACDispatcher(this);
    }

    public static class Builder extends Commandler.Builder<Builder, GenericMessageEvent, Message> {

        @Override
        public JDAC build() {
            initializeDefaults();
            return new JDAC(this);
        }
    }
}

