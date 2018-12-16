package com.elypia.jdac.parsing;

import com.elypia.commandler.annotations.Compatible;
import com.elypia.commandler.metadata.ParamData;
import com.elypia.jdac.Scope;
import com.elypia.jdac.alias.*;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.*;
import java.util.stream.Collectors;

@Compatible(Role.class)
public class RoleParser implements IJDACEntityParser<Role> {

    @Override
    public Role parse(JDACEvent event, ParamData data, Class<? extends Role> type, String input) {
        MessageReceivedEvent source = (MessageReceivedEvent)event.getSource();
        Collection<Role> roles = new ArrayList<>();

        switch (getScope(data, Scope.LOCAL)) {
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
