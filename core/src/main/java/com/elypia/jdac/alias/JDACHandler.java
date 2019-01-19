package com.elypia.jdac.alias;

import com.elypia.commandler.*;
import com.elypia.commandler.interfaces.ICommandEvent;
import com.elypia.commandler.metadata.ModuleData;
import com.elypia.jdac.JDACUtils;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.GenericMessageEvent;

public class JDACHandler extends Handler<GenericMessageEvent, Message> {

    /**
     * Initialise the module, this will assign the values
     * in the module and create a {@link ModuleData} which is
     * what {@link Commandler} uses in runtime to identify modules,
     * commands or obtain any static data.
     *
     * @param commandler Our parent Commandler class.
     */
    public JDACHandler(Commandler<GenericMessageEvent, Message> commandler) {
        super(commandler);
    }

    @Override
    public Object help(ICommandEvent<GenericMessageEvent, Message> event) {
        if (!JDACUtils.canSendEmbeds(event.getSource()))
            return super.help(event);

        return super.help(event); // temp
    }
}
