package com.elypia.jdac.alias;

import com.elypia.commandler.Commandler;
import com.elypia.commandler.impl.CommandProcessor;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.GenericMessageEvent;

public class JDACProcessor extends CommandProcessor<JDA, GenericMessageEvent, Message> {

    public JDACProcessor(JDAC commandler) {
        super(commandler);
    }

    @Override
    public JDACEvent process(Commandler<JDA, GenericMessageEvent, Message> commandler, GenericMessageEvent source, String content) {
        var event = super.process(commandler, source, content);
        return new JDACEvent(event);
    }
}
