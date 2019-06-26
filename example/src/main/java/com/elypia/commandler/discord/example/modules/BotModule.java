package com.elypia.commandler.discord.example.modules;

import com.elypia.commandler.annotations.Module;
import com.elypia.commandler.annotations.*;
import com.elypia.commandler.interfaces.Handler;

@Module(name = "Bot", aliases = "bot", help = "Basic bot commands to check status.")
public class BotModule implements Handler {

    @Static
    @Command(name = "ping!", aliases = "ping", help = "Check if I'm alive!")
    public String ping() {
        return "pong!";
    }

    @Command(name = "Say", aliases = "say", help = "Repeat back the parameter provided.")
    public String say(@Param(name = "body", help = "What to say.") String body) {
        return body;
    }
}
