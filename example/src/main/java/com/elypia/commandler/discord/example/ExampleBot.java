package com.elypia.commandler.discord.example;

import com.elypia.commandler.*;
import com.elypia.commandler.adapters.*;
import com.elypia.commandler.discord.DiscordController;
import com.elypia.commandler.discord.adapters.*;
import com.elypia.commandler.discord.example.modules.BotModule;
import com.elypia.commandler.discord.providers.*;
import com.elypia.commandler.loader.AnnotationLoader;
import com.elypia.commandler.metadata.ContextLoader;
import com.elypia.commandler.modules.HelpModule;
import com.elypia.commandler.providers.*;
import net.dv8tion.jda.api.*;

import javax.security.auth.login.LoginException;
import java.io.IOException;

public class ExampleBot {

    public static final Class<?>[] TYPES = {
        BooleanAdapter.class,
        CharAdapter.class,
        DurationAdapter.class,
        EnumAdapter.class,
        MetaCommandAdapter.class,
        MetaModuleAdapter.class,
        NumberAdapter.class,
        StringAdapter.class,
        TimeUnitAdapter.class,
        UrlAdapter.class,
        MiscToStringProvider.class,
        NumberToStringProvider.class,
        HelpModule.class,

        EmoteAdapter.class,
        GuildAdapter.class,
        RoleAdapter.class,
        TextChannelAdapter.class,
        UserAdapter.class,
        VoiceChannelAdapter.class,
        EmbedBuilderProvider.class,
        MessageEmbedProvider.class,
        MessageProvider.class,
        MiscToMessageProvider.class,

        BotModule.class
    };

    public static void main(String[] args) throws LoginException, IOException {
        // Create a loader, this creates an annotation loader which loads from the listed classes.
        AnnotationLoader annoLoader = new AnnotationLoader(TYPES);

        // Create a context providing which loads and provides all static data to Commandler.
        Context context = new ContextLoader(annoLoader).load().build();

        // Create the Commandler instance with our context.
        Commandler commandler = new Commandler(context);

        // Login to Discord so we can accept commands from 2 places.
        JDA jda = new JDABuilder(args[0]).build();

        new DiscordController(commandler.getDispatchManager(), jda);
    }
}
