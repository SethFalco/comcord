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

@Adapter(TextChannel.class)
public class TextChannelAdapter implements EntityAdapter<TextChannel> {

    @Override
    public TextChannel adapt(String input, Class<? extends TextChannel> type, MetaParam data, CommandlerEvent<?> event) {
        MessageReceivedEvent source = (MessageReceivedEvent)event.getSource();
        Collection<TextChannel> channels = new ArrayList<>();

        switch (getScope(event, data, Scope.LOCAL)) {
            case GLOBAL: {
                channels.addAll(source.getJDA().getTextChannels());
                break;
            }
            case MUTUAL: {
                channels.addAll(source.getAuthor().getMutualGuilds()
                    .parallelStream()
                    .map(Guild::getTextChannels)
                    .flatMap(List::stream)
                    .collect(Collectors.toSet()));
                break;
            }
            case LOCAL: {
                channels.addAll(source.getGuild().getTextChannels());
                break;
            }
            default: {
                throw new IllegalStateException("Unmanaged search scope.");
            }
        }

        return filter(channels, type, input, role ->
            role.getName().equalsIgnoreCase(input)
        );
    }
}
