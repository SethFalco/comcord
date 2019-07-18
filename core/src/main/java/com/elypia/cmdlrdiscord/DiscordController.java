package com.elypia.cmdlrdiscord;

import com.elypia.commandler.interfaces.Controller;
import com.elypia.commandler.managers.DispatcherManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;

public class DiscordController implements Controller<Message> {

    public DiscordController(DispatcherManager dispatcher, JDA jda) {
        jda.addEventListener(new DiscordListener(dispatcher, this));
    }

    @Override
    public Class<Message> getMessageType() {
        return Message.class;
    }
}
