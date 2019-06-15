package com.elypia.jdac;

import com.elypia.commandler.interfaces.IBuilder;
import net.dv8tion.jda.api.entities.Message;

public interface IJDACBuilder<O> extends ResponseP<JDACEvent, O, Message> {

    default Message buildEmbed(JDACEvent event, O output) {
        return null;
    }
}
