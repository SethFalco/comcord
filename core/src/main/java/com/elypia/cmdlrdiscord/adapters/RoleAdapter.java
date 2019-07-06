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

@Adapter(Role.class)
public class RoleAdapter implements EntityAdapter<Role> {

    @Override
    public Role adapt(String input, Class<? extends Role> type, MetaParam data, CommandlerEvent<?> event) {
        MessageReceivedEvent source = (MessageReceivedEvent)event.getSource();
        Collection<Role> roles = new ArrayList<>();

        switch (getScope(event, data, Scope.LOCAL)) {
            case GLOBAL: {
                roles.addAll(source.getJDA().getRoles());
                break;
            }
            case MUTUAL: {
                roles.addAll(source.getAuthor().getMutualGuilds()
                    .parallelStream()
                    .map(Guild::getRoles)
                    .flatMap(List::stream)
                    .collect(Collectors.toSet()));
                break;
            }
            case LOCAL: {
                roles.addAll(source.getGuild().getRoles());
                break;
            }
            default: {
                throw new IllegalStateException("Unmanaged search scope.");
            }
        }

        return filter(roles, type, input, role ->
            role.getName().equalsIgnoreCase(input)
        );
    }
}
