/*
 * Copyright 2019-2020 Elypia CIC
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
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.Event;
import org.elypia.comcord.EventUtils;
import org.elypia.comcord.constraints.Elevated;
import org.elypia.commandler.event.ActionEvent;

import javax.inject.Inject;
import javax.validation.*;

/**
 * This is not intended for checking specific permissions,
 * but rather as a generic way to determine if a user is
 * allowed to configure parts of the bot.
 *
 * See {@link org.elypia.comcord.constraints.Permissions} for checking permissions.
 *
 * @author seth@elypia.org (Seth Falco)
 */
public class ElevatedValidator implements ConstraintValidator<Elevated, ActionEvent<Event, ?>> {

    @Inject
    private JDA jda;

    private long ownerId;

    public ElevatedValidator() {
        // Do nothing
    }

    @Override
    public void initialize(Elevated elevated) {
        ApplicationInfo info = jda.retrieveApplicationInfo().complete();
        ownerId = info.getOwner().getIdLong();
    }

    @Override
    public boolean isValid(ActionEvent<Event, ?> value, ConstraintValidatorContext context) {
        Event source = value.getRequest().getSource();

        if (EventUtils.getGuild(source) == null)
            return true;

        TextChannel channel = EventUtils.getTextChannel(source);

        if (EventUtils.getMember(source).hasPermission(channel, Permission.MANAGE_SERVER))
            return true;

        User user = EventUtils.getAuthor(source);
        return user.getIdLong() == ownerId;
    }
}
