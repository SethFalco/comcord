package com.elypia.commandler.discord.adapters;

import com.elypia.commandler.CommandlerEvent;
import com.elypia.commandler.annotations.Adapter;
import com.elypia.commandler.discord.Scope;
import com.elypia.commandler.discord.interfaces.EntityAdapter;
import com.elypia.commandler.metadata.data.MetaParam;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.*;

@Adapter(Guild.class)
public class GuildAdapter implements EntityAdapter<Guild> {

    @Override
    public Guild adapt(String input, Class<? extends Guild> type, MetaParam data, CommandlerEvent<?> event) {
        MessageReceivedEvent source = (MessageReceivedEvent)event.getSource();
        Collection<Guild> guilds = new ArrayList<>();

        switch (getScope(event, data, Scope.MUTUAL)) {
            case GLOBAL: {
                guilds.addAll(source.getJDA().getGuilds());
                break;
            }
            case MUTUAL: {
                guilds.addAll(source.getAuthor().getMutualGuilds());
                break;
            }
            case LOCAL: {
                throw new IllegalStateException("Can't search for guilds locally.");
            }
            default: {
                throw new IllegalStateException("Unmanaged search scope.");
            }
        }

        return filter(guilds, type, input, guild ->
            guild.getName().equalsIgnoreCase(input)
        );
    }
}
