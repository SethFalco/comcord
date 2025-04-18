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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.elypia.commandler.annotation.stereotypes.ParamAdapter;
import org.elypia.commandler.event.ActionEvent;
import org.elypia.commandler.metadata.MetaParam;

import fun.falco.comcord.EventUtils;
import fun.falco.comcord.Scope;
import fun.falco.comcord.api.EntityAdapter;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.GenericEvent;

/**
 * @author seth@falco.fun (Seth Falco)
 */
@ParamAdapter(Role.class)
public class RoleAdapter implements EntityAdapter<Role> {

    @Override
    public Role adapt(String input, Class<? extends Role> type, MetaParam data, ActionEvent<?, ?> event) {
        GenericEvent source = (GenericEvent) event.getRequest().getSource();
        Collection<Role> roles = new ArrayList<>();

        switch (getScope(event, data, Scope.LOCAL)) {
            case GLOBAL:
                roles.addAll(source.getJDA().getRoles());
                break;
            case MUTUAL:
                roles.addAll(EventUtils.getAuthor(source).getMutualGuilds()
                    .stream()
                    .map(Guild::getRoles)
                    .flatMap(List::stream)
                    .collect(Collectors.toSet()));
                break;
            case LOCAL:
                Guild guild = EventUtils.getGuild(source);

                if (guild == null) {
                    return null;
                }

                roles.addAll(guild.getRoles());
                break;
            default:
                throw new IllegalStateException("Unmanaged search scope.");
        }

        return filter(roles, type, input, role ->
            role.getName().equalsIgnoreCase(input)
        );
    }
}
