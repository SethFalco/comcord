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

import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.Event;
import org.elypia.comcord.EventUtils;
import org.elypia.comcord.constraints.Elevated;
import org.elypia.commandler.event.ActionEvent;

import javax.inject.*;
import javax.validation.*;

/**
 * @author seth@elypia.org (Seth Falco)
 */
@Singleton
public class ElevatedValidator implements ConstraintValidator<Elevated, ActionEvent<Event, ?>> {

    private final JDA jda;

    @Inject
    public ElevatedValidator(final JDA jda) {
        this.jda = jda;
    }

    @Override
    public boolean isValid(ActionEvent<Event, ?> value, ConstraintValidatorContext context) {
        Event source = value.getRequest().getSource();

        if (EventUtils.getGuild(source) != null)
            return true;

        TextChannel channel = EventUtils.getTextChannel(source);

        if (EventUtils.getMember(source).hasPermission(channel, Permission.MANAGE_SERVER))
            return true;

        return new BotOwnerValidator(jda).isValid(value, context);
    }
}
