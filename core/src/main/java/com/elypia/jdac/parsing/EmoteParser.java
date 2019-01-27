package com.elypia.jdac.parsing;

import com.elypia.commandler.annotations.Compatible;
import com.elypia.commandler.metadata.ParamData;
import com.elypia.jdac.IJDACEntityParser;
import com.elypia.jdac.JDACEvent;
import com.elypia.jdac.Scope;
import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Compatible(Emote.class)
public class EmoteParser implements IJDACEntityParser<Emote> {

    @Override
    public Emote parse(JDACEvent event, ParamData data, Class<? extends Emote> type, String input) {
        MessageReceivedEvent source = (MessageReceivedEvent)event.getSource();
        Set<Emote> emotes = new HashSet<>(source.getMessage().getEmotes());

        switch (getScope(data, Scope.MUTUAL)) {
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
