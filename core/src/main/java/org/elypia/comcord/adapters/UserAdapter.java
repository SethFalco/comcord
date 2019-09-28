/*
 * Copyright 2019-2019 Elypia CIC
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
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.elypia.comcord.Scope;
import org.elypia.comcord.interfaces.EntityAdapter;
import org.elypia.commandler.CommandlerEvent;
import org.elypia.commandler.annotations.Adapter;
import org.elypia.commandler.metadata.MetaParam;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author seth@elypia.org (Syed Shah)
 */
@Adapter(User.class)
public class UserAdapter implements EntityAdapter<User> {

    // TODO: Accomodate MessageUpdateEvent
    // TODO: Assumes you are in a guild/text channel
    // TODO: Check effective name
    // TODO: Empty quotes doesn't parse correctly
    // TODO: CommandlerEvent is not being injected @Everyone
    @Override
    public User adapt(String input, Class<? extends User> type, MetaParam data, CommandlerEvent<?, ?> event) {
        MessageReceivedEvent source = (MessageReceivedEvent)event.getSource();
        Collection<User> users = new ArrayList<>();

        switch (getScope(event, data, Scope.LOCAL)) {
            case GLOBAL: {
                users.addAll(source.getJDA().getUsers());
                break;
            }
            case MUTUAL: {
                users.addAll(source.getAuthor().getMutualGuilds()
                    .parallelStream()
                    .map(Guild::getMembers)
                    .flatMap(List::stream)
                    .map(Member::getUser)
                    .collect(Collectors.toSet()));
                break;
            }
            case LOCAL: {
                users.addAll(source.getGuild().getMembers().parallelStream()
                    .map(Member::getUser).collect(Collectors.toSet()));
                break;
            }
            default: {
                throw new IllegalStateException("Unmanaged search scope.");
            }
        }

        return filter(users, type, input, role ->
            role.getName().equalsIgnoreCase(input) || ("<@!" + role.getId() + ">").equals(input)
        );
    }
}
