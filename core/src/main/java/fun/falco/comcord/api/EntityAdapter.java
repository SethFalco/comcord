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

package fun.falco.comcord.api;

import java.util.Collection;
import java.util.Iterator;
import java.util.function.Predicate;

import org.elypia.commandler.api.Adapter;
import org.elypia.commandler.event.ActionEvent;
import org.elypia.commandler.metadata.MetaParam;

import fun.falco.comcord.Scope;
import fun.falco.comcord.annotations.Scoped;
import net.dv8tion.jda.api.entities.IMentionable;
import net.dv8tion.jda.api.entities.ISnowflake;
import net.dv8tion.jda.api.events.guild.GenericGuildEvent;

/**
 * @author seth@falco.fun (Seth Falco)
 */
public interface EntityAdapter<O> extends Adapter<O> {

    O adapt(String input, Class<? extends O> type, MetaParam data, ActionEvent<?, ?> event);

    default Scope getScope(ActionEvent<?, ?> event, MetaParam data, Scope defaultScope) {
        Scoped scoped = data.getParameter().getAnnotation(Scoped.class);

        if (scoped == null) {
            return defaultScope;
        }

        return (event.getRequest().getSource() instanceof GenericGuildEvent) ? scoped.inGuild() : scoped.inPrivate();
    }

    default O filter(Collection<O> collection, Class<? extends O> type, String input, Predicate<O> predicate) {
        Iterator<O> matches = collection.stream().filter(o ->
            predicate.test(o) ||
            (IMentionable.class.isAssignableFrom(type) && ((IMentionable) o).getAsMention().equalsIgnoreCase(input)) ||
            (ISnowflake.class.isAssignableFrom(type) && ((ISnowflake) o).getId().equals(input))
        ).iterator();

        return matches.hasNext() ? matches.next() : null;
    }
}
