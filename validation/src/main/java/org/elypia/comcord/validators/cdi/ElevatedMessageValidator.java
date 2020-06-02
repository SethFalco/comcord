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

package org.elypia.comcord.validators.cdi;

import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.entities.*;
import org.elypia.comcord.constraints.Elevated;

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
public class ElevatedMessageValidator implements ConstraintValidator<Elevated, Message> {

    private long ownerId;

    @Inject
    public ElevatedMessageValidator(JDA jda) {
        ApplicationInfo info = jda.retrieveApplicationInfo().complete();
        ownerId = info.getOwner().getIdLong();
    }

    @Override
    public boolean isValid(Message message, ConstraintValidatorContext context) {
        if (!message.isFromGuild())
            return true;

        Member member = message.getMember();

        if (member == null)
            throw new IllegalStateException("Non-null message and non-null guild returned null member.");

        TextChannel channel = message.getTextChannel();

        if (member.hasPermission(channel, Permission.MANAGE_SERVER))
            return true;

        User user = member.getUser();
        return user.getIdLong() == ownerId;
    }
}
