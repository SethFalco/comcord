package com.elypia.jdac.alias;

import com.elypia.commandler.metadata.ParamData;
import com.elypia.jdac.*;
import net.dv8tion.jda.core.entities.*;

import java.util.*;
import java.util.function.Predicate;

public interface IJDACEntityParser<O> extends IJDACParser<O> {

    O parse(JDACEvent event, ParamData data, Class<? extends O> type, String input);

    default Scope getScope(ParamData data, Scope defaultScope) {
        Search search = data.getParameter().getAnnotation(Search.class);
        return search != null ? search.value() : defaultScope;
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
