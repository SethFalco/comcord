package com.elypia.jdac.test.impl.module;

import com.elypia.commandler.Commandler;
import com.elypia.commandler.annotations.Command;
import com.elypia.commandler.annotations.Module;
import com.elypia.commandler.annotations.Param;
import com.elypia.commandler.metadata.ModuleData;
import com.elypia.jdac.JDACHandler;
import com.elypia.jdac.validation.Talkable;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.GenericMessageEvent;

@Module(id = "Validation", aliases = "valid")
public class ValidationModule extends JDACHandler {

    /**
     * Initialise the module, this will assign the values
     * in the module and create a {@link ModuleData} which is
     * what {@link Commandler} uses in runtime to identify modules,
     * commands or obtain any static data.
     *
     * @param commandler Our parent Commandler class.
     */
    public ValidationModule(Commandler<GenericMessageEvent, Message> commandler) {
        super(commandler);
    }

    @Command(id = "Talkable", aliases = "talkable")
    public String talkable(
        @Param(id = "channel") @Talkable TextChannel channel
    ) {
        return "Channel!";
    }
}
