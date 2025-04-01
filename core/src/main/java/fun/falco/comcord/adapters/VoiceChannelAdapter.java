/*
 * Copyright 2019-2025 Seth Falco and Comcord Contributors
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

package fun.falco.comcord.adapters;

import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.GenericEvent;
import fun.falco.comcord.*;
import fun.falco.comcord.api.EntityAdapter;
import org.elypia.commandler.annotation.stereotypes.ParamAdapter;
import org.elypia.commandler.event.ActionEvent;
import org.elypia.commandler.metadata.MetaParam;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author seth@falco.fun (Seth Falco)
 */
@ParamAdapter(VoiceChannel.class)
public class VoiceChannelAdapter implements EntityAdapter<VoiceChannel> {

    @Override
    public VoiceChannel adapt(String input, Class<? extends VoiceChannel> type, MetaParam data, ActionEvent<?, ?> event) {
        GenericEvent source = (GenericEvent)event.getRequest().getSource();
        Collection<VoiceChannel> channels = new ArrayList<>();

        switch (getScope(event, data, Scope.LOCAL)) {
            case GLOBAL:
                channels.addAll(source.getJDA().getVoiceChannels());
                break;
            case MUTUAL:
                channels.addAll(EventUtils.getAuthor(source).getMutualGuilds()
                    .stream()
                    .map(Guild::getVoiceChannels)
                    .flatMap(List::stream)
                    .collect(Collectors.toSet()));
                break;
            case LOCAL:
                Guild guild = EventUtils.getGuild(source);

                if (guild == null)
                    return null;

                channels.addAll(guild.getVoiceChannels());
                break;
            default:
                throw new IllegalStateException("Unmanaged search scope.");
        }

        return filter(channels, type, input, role ->
            role.getName().equalsIgnoreCase(input)
        );
    }
}
