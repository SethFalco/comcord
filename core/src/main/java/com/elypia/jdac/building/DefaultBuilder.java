package com.elypia.jdac.building;

import com.elypia.commandler.annotations.Compatible;
import com.elypia.jdac.alias.*;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Message;

@Compatible(String.class)
public class DefaultBuilder implements IJDACBuilder<String> {

    @Override
    public Message build(JDACEvent jdacEvent, String output) {
        return new MessageBuilder(output).build();
    }
}
