package com.elypia.jdac;

import com.elypia.commandler.CommandInput;
import com.elypia.commandler.Commandler;
import com.elypia.commandler.impl.CommandProcessor;
import com.elypia.commandler.interfaces.ICommandEvent;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.GenericMessageEvent;

public class JDACProcessor extends CommandProcessor<GenericMessageEvent, Message> {

    public JDACProcessor(JDAC commandler) {
        super(commandler);
    }

    @Override
    public ICommandEvent<GenericMessageEvent, Message> spawnEvent(Commandler<GenericMessageEvent, Message> commandler, GenericMessageEvent source, CommandInput input) {
        return new JDACEvent(commandler, source, input);
    }
}
