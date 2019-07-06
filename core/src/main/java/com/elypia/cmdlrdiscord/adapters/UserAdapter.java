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

@Adapter(User.class)
public class UserAdapter implements EntityAdapter<User> {

    @Override
    public User adapt(String input, Class<? extends User> type, MetaParam data, CommandlerEvent<?> event) {
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
            role.getName().equalsIgnoreCase(input)
        );
    }
}
