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
 * @author seth@falco.fun (Seth Falco)
 */
@ParamAdapter(User.class)
public class UserAdapter implements EntityAdapter<User> {

    @Override
    public User adapt(String input, Class<? extends User> type, MetaParam data, ActionEvent<?, ?> event) {
        GenericEvent source = (GenericEvent)event.getRequest().getSource();
        Collection<User> users = new ArrayList<>();

        switch (getScope(event, data, Scope.LOCAL)) {
            case GLOBAL:
                users.addAll(source.getJDA().getUsers());
                break;
            case MUTUAL:
                users.addAll(EventUtils.getAuthor(source).getMutualGuilds()
                    .stream()
                    .map(Guild::getMembers)
                    .flatMap(List::stream)
                    .map(Member::getUser)
                    .collect(Collectors.toSet()));
                break;
            case LOCAL:
                Guild guild = EventUtils.getGuild(source);

                if (guild == null)
                    return null;

                users.addAll(EventUtils.getGuild(source).getMembers().stream()
                    .map(Member::getUser).collect(Collectors.toSet()));
                break;
            default:
                throw new IllegalStateException("Unmanaged search scope.");
        }

        return filter(users, type, input, role ->
            role.getName().equalsIgnoreCase(input) || ("<@!" + role.getId() + ">").equals(input)
        );
    }
}
