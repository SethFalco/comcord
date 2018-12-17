package com.elypia.jdac.alias;

import com.elypia.commandler.Commandler;
import com.elypia.jdac.building.*;
import com.elypia.jdac.parsing.*;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.GenericMessageEvent;

public class JDAC extends Commandler<GenericMessageEvent, Message> {

    public JDAC(Builder jdacBuilder) {
        super(jdacBuilder);

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

    public static class Builder extends Commandler.Builder<GenericMessageEvent, Message> {

        @Override
        public JDAC build() {
            initializeDefaults();
            return new JDAC(this);
        }
    }
}
