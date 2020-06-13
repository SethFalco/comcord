/*
 * Copyright 2019-2020 Elypia CIC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.elypia.comcord.adapters;

import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.GenericEvent;
import org.elypia.comcord.*;
import org.elypia.comcord.api.EntityAdapter;
import org.elypia.commandler.annotation.stereotypes.ParamAdapter;
import org.elypia.commandler.event.ActionEvent;
import org.elypia.commandler.metadata.MetaParam;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author seth@elypia.org (Seth Falco)
 */
@ParamAdapter(Emote.class)
public class EmoteAdapter implements EntityAdapter<Emote> {

    @Override
    public Emote adapt(String input, Class<? extends Emote> type, MetaParam data, ActionEvent<?, ?> event) {
        GenericEvent source = (GenericEvent)event.getRequest().getSource();
        Set<Emote> emotes = new HashSet<>(EventUtils.getMessage(source).getEmotes());

        switch (getScope(event, data, Scope.MUTUAL)) {
            case GLOBAL:
                emotes.addAll(source.getJDA().getEmotes());
                break;
            case MUTUAL:
                emotes.addAll(EventUtils.getAuthor(source).getMutualGuilds()
                    .stream()
                    .map(Guild::getEmotes)
                    .flatMap(List::stream)
                    .collect(Collectors.toSet()));
                break;
            case LOCAL:
                Guild guild = EventUtils.getGuild(source);

                if (guild == null)
                    return null;

                emotes.addAll(guild.getEmotes());
                break;
            default:
                throw new IllegalStateException("Unmanaged search scope.");
        }

        return filter(emotes, type, input, emote ->
            emote.getName().equalsIgnoreCase(input)
        );
    }
}
