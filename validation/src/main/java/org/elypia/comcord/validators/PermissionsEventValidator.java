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

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.Event;
import org.elypia.comcord.EventUtils;
import org.elypia.comcord.constraints.Permissions;
import org.elypia.commandler.event.ActionEvent;

import javax.inject.Singleton;
import javax.validation.*;

/**
 * @author seth@elypia.org (Seth Falco)
 */
@Singleton
public class PermissionsEventValidator implements ConstraintValidator<Permissions, ActionEvent<Event, ?>> {

    private Permission[] permissions;
    private boolean user;

    @Override
    public void initialize(Permissions constraintAnnotation) {
        permissions = constraintAnnotation.value();
        user = constraintAnnotation.user();
    }

    @Override
    public boolean isValid(ActionEvent<Event, ?> value, ConstraintValidatorContext context) {
        Event source = value.getRequest().getSource();
        Guild guild = EventUtils.getGuild(source);

        if (guild == null)
            return true;

        TextChannel channel = EventUtils.getTextChannel(source);

        if (!guild.getSelfMember().hasPermission(channel, permissions))
            return false;

        if (!user)
            return true;

        Member member = EventUtils.getMember(source);
        return member.hasPermission(channel, permissions);
    }
}
