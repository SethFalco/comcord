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

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.Event;
import org.elypia.comcord.*;
import org.elypia.comcord.api.EntityAdapter;
import org.elypia.commandler.event.ActionEvent;
import org.elypia.commandler.metadata.MetaParam;

import javax.inject.Singleton;
import java.util.*;

/**
 * @author seth@elypia.org (Seth Falco)
 */
@Singleton
public class GuildAdapter implements EntityAdapter<Guild> {

    @Override
    public Guild adapt(String input, Class<? extends Guild> type, MetaParam data, ActionEvent<?, ?> event) {
        Event source = (Event)event.getRequest().getSource();
        Collection<Guild> guilds = new ArrayList<>();

        switch (getScope(event, data, Scope.MUTUAL)) {
            case GLOBAL:
                guilds.addAll(source.getJDA().getGuilds());
                break;
            case MUTUAL:
                guilds.addAll(EventUtils.getAuthor(source).getMutualGuilds());
                break;
            case LOCAL:
                throw new IllegalStateException("Can't search for guilds locally.");
            default:
                throw new IllegalStateException("Unmanaged search scope.");
        }

        return filter(guilds, type, input, guild ->
            guild.getName().equalsIgnoreCase(input)
        );
    }
}
