package com.elypia.jdac.building;

import com.elypia.commandler.annotations.Compatible;
import com.elypia.jdac.IJDACBuilder;
import com.elypia.jdac.JDACEvent;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Message;

@Compatible(String.class)
public class DefaultBuilder implements IJDACBuilder<String> {

    @Override
    public Message build(JDACEvent jdacEvent, String output) {
        return new MessageBuilder(output).build();
    }
}
