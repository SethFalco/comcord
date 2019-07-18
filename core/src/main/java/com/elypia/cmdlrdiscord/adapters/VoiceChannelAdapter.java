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

@Adapter(VoiceChannel.class)
public class VoiceChannelAdapter implements EntityAdapter<VoiceChannel> {

    @Override
    public VoiceChannel adapt(String input, Class<? extends VoiceChannel> type, MetaParam data, CommandlerEvent<?, ?> event) {
        MessageReceivedEvent source = (MessageReceivedEvent)event.getSource();
        Collection<VoiceChannel> channels = new ArrayList<>();

        switch (getScope(event, data, Scope.LOCAL)) {
            case GLOBAL: {
                channels.addAll(source.getJDA().getVoiceChannels());
                break;
            }
            case MUTUAL: {
                channels.addAll(source.getAuthor().getMutualGuilds()
                    .parallelStream()
                    .map(Guild::getVoiceChannels)
                    .flatMap(List::stream)
                    .collect(Collectors.toSet()));
                break;
            }
            case LOCAL: {
                channels.addAll(source.getGuild().getVoiceChannels());
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
