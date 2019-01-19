package com.elypia.jdac.parsing;

import com.elypia.commandler.annotations.Compatible;
import com.elypia.commandler.metadata.ParamData;
import com.elypia.jdac.Scope;
import com.elypia.jdac.alias.*;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.*;
import java.util.stream.Collectors;

@Compatible(VoiceChannel.class)
public class VoiceChannelParser implements IJDACEntityParser<VoiceChannel> {

    @Override
    public VoiceChannel parse(JDACEvent event, ParamData data, Class<? extends VoiceChannel> type, String input) {
        MessageReceivedEvent source = (MessageReceivedEvent)event.getSource();
        Collection<VoiceChannel> channels = new ArrayList<>();

        switch (getScope(data, Scope.LOCAL)) {
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
