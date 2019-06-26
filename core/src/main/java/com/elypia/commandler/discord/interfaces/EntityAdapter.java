package com.elypia.commandler.discord.interfaces;

import com.elypia.commandler.CommandlerEvent;
import com.elypia.commandler.discord.Scope;
import com.elypia.commandler.discord.annotations.Search;
import com.elypia.commandler.interfaces.ParamAdapter;
import com.elypia.commandler.metadata.data.MetaParam;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.guild.GenericGuildEvent;

import java.util.*;
import java.util.function.Predicate;

public interface EntityAdapter<O> extends ParamAdapter<O> {

    O adapt(String input, Class<? extends O> type, MetaParam data, CommandlerEvent<?> event);

    default Scope getScope(CommandlerEvent<?> event, MetaParam data, Scope defaultScope) {
        Search search = data.getAnnotatedElement().getAnnotation(Search.class);

        if (search == null)
            return defaultScope;

        return (event.getSource() instanceof GenericGuildEvent) ? search.inGuild() : search.inPrivate();
    }

    default O filter(Collection<O> collection, Class<? extends O> type, String input, Predicate<O> predicate) {
        Iterator<O> matches = collection.parallelStream().filter(o ->
            predicate.test(o) ||
            (IMentionable.class.isAssignableFrom(type) && ((IMentionable)o).getAsMention().equalsIgnoreCase(input)) ||
            (ISnowflake.class.isAssignableFrom(type) && ((ISnowflake)o).getId().equals(input))
        ).iterator();

        return matches.hasNext() ? matches.next() : null;
    }
}
