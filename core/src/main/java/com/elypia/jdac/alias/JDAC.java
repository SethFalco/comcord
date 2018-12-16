package com.elypia.jdac.alias;

import com.elypia.commandler.Commandler;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.GenericMessageEvent;

public class JDAC extends Commandler<GenericMessageEvent, Message> {

    public JDAC(Builder builder) {
        super(builder);
    }

    public static class Builder extends Commandler.Builder<GenericMessageEvent, Message> {

        @Override
        public JDAC build() {
            initializeDefaults();
            return new JDAC(this);
        }
    }
}
