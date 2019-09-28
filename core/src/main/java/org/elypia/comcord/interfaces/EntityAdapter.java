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

package org.elypia.comcord.interfaces;

import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.guild.GenericGuildEvent;
import org.elypia.comcord.Scope;
import org.elypia.comcord.annotations.Scoped;
import org.elypia.commandler.CommandlerEvent;
import org.elypia.commandler.interfaces.ParamAdapter;
import org.elypia.commandler.metadata.MetaParam;

import java.util.*;
import java.util.function.Predicate;

/**
 * @author seth@elypia.org (Syed Shah)
 */
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
