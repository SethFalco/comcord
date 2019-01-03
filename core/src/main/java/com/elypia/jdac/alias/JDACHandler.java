package com.elypia.jdac.alias;

import com.elypia.commandler.Handler;
import com.elypia.commandler.interfaces.ICommandEvent;
import com.elypia.jdac.JDACUtils;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.GenericMessageEvent;

public class JDACHandler extends Handler<GenericMessageEvent, Message> {

    @Override
    public Object help(ICommandEvent<GenericMessageEvent, Message> event) {
        if (!JDACUtils.canSendEmbeds(event.getSource()))
            return super.help(event);

        return super.help(event); // temp
    }
}
