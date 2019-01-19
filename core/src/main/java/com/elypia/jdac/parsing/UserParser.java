package com.elypia.jdac.parsing;

import com.elypia.commandler.annotations.Compatible;
import com.elypia.commandler.metadata.ParamData;
import com.elypia.jdac.Scope;
import com.elypia.jdac.alias.*;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.*;
import java.util.stream.Collectors;

@Compatible(User.class)
public class UserParser implements IJDACEntityParser<User> {

    @Override
    public User parse(JDACEvent event, ParamData data, Class<? extends User> type, String input) {
        MessageReceivedEvent source = (MessageReceivedEvent)event.getSource();
        Collection<User> users = new ArrayList<>();

        switch (getScope(data, Scope.LOCAL)) {
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
            role.getName().equalsIgnoreCase(input)
        );
    }
}
