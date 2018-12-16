package com.elypia.jdac.alias;

import com.elypia.commandler.interfaces.IBuilder;
import net.dv8tion.jda.core.entities.Message;

public interface IJDACBuilder<O> extends IBuilder<JDACEvent, O, Message> {

    default Message buildEmbed(JDACEvent event, O output) {
        return null;
    }
}
