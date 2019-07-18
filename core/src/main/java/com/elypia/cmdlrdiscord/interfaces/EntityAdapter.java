package com.elypia.cmdlrdiscord.interfaces;

import com.elypia.cmdlrdiscord.Scope;
import com.elypia.cmdlrdiscord.annotations.Scoped;
import com.elypia.commandler.CommandlerEvent;
import com.elypia.commandler.interfaces.ParamAdapter;
import com.elypia.commandler.metadata.MetaParam;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.guild.GenericGuildEvent;

import java.util.*;
import java.util.function.Predicate;

public interface EntityAdapter<O> extends ParamAdapter<O> {

    O adapt(String input, Class<? extends O> type, MetaParam data, CommandlerEvent<?, ?> event);

    default Scope getScope(CommandlerEvent<?, ?> event, MetaParam data, Scope defaultScope) {
        Scoped scoped = data.getAnnotatedElement().getAnnotation(Scoped.class);

        if (scoped == null)
            return defaultScope;

        return (event.getSource() instanceof GenericGuildEvent) ? scoped.inGuild() : scoped.inPrivate();
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
