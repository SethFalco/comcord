package com.elypia.cmdlrdiscord.adapters;

import com.elypia.cmdlrdiscord.Scope;
import com.elypia.cmdlrdiscord.interfaces.EntityAdapter;
import com.elypia.commandler.CommandlerEvent;
import com.elypia.commandler.annotations.Adapter;
import com.elypia.commandler.metadata.MetaParam;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.*;
import java.util.stream.Collectors;

// TODO: Make a way to specify "all" as a list argument.
// TODO: Make a way that adapters can return a list of many arguments.
@Adapter(Emote.class)
public class EmoteAdapter implements EntityAdapter<Emote> {

    @Override
    public Emote adapt(String input, Class<? extends Emote> type, MetaParam data, CommandlerEvent<?, ?> event) {
        MessageReceivedEvent source = (MessageReceivedEvent)event.getSource();
        Set<Emote> emotes = new HashSet<>(source.getMessage().getEmotes());

        switch (getScope(event, data, Scope.MUTUAL)) {
            case GLOBAL: {
                emotes.addAll(source.getJDA().getEmotes());
                break;
            }
            case MUTUAL: {
                emotes.addAll(source.getAuthor().getMutualGuilds()
                    .parallelStream()
                    .map(Guild::getEmotes)
                    .flatMap(List::stream)
                    .collect(Collectors.toSet()));
                break;
            }
            case LOCAL: {
                emotes.addAll(source.getGuild().getEmotes());
                break;
            }
            default: {
                throw new IllegalStateException("Unmanaged search scope.");
            }
        }

        return filter(emotes, type, input, emote ->
            emote.getName().equalsIgnoreCase(input)
        );
    }
}
