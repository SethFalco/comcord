package com.elypia.jdac.parsing;

import com.elypia.commandler.annotations.Compatible;
import com.elypia.commandler.metadata.ParamData;
import com.elypia.jdac.IJDACEntityParser;
import com.elypia.jdac.JDACEvent;
import com.elypia.jdac.Scope;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.ArrayList;
import java.util.Collection;

@Compatible(Guild.class)
public class GuildParser implements IJDACEntityParser<Guild> {

    @Override
    public Guild parse(JDACEvent event, ParamData data, Class<? extends Guild> type, String input) {
        MessageReceivedEvent source = (MessageReceivedEvent)event.getSource();
        Collection<Guild> guilds = new ArrayList<>();

        switch (getScope(data, Scope.MUTUAL)) {
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
