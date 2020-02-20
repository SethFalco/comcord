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

package org.elypia.comcord.validators;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.Event;
import org.elypia.comcord.EventUtils;
import org.elypia.comcord.constraints.BotOwner;
import org.elypia.commandler.event.ActionEvent;

import javax.inject.*;
import javax.validation.*;

/**
 * @author seth@elypia.org (Seth Falco)
 */
@Singleton
public class BotOwnerValidator implements ConstraintValidator<BotOwner, ActionEvent<Event, ?>> {

    private final long ownerId;

    @Inject
    public BotOwnerValidator(final JDA jda) {
        ApplicationInfo info = jda.retrieveApplicationInfo().complete();
        ownerId = info.getOwner().getIdLong();
    }

    @Override
    public boolean isValid(ActionEvent<Event, ?> value, ConstraintValidatorContext context) {
        Event source = value.getRequest().getSource();
        User user = EventUtils.getAuthor(source);

        if (user == null)
            throw new IllegalStateException("Author returned null while performing bot owner validation.");

        return user.getIdLong() == ownerId;
    }
}
