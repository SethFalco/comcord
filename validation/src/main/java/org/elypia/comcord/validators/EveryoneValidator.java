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
import org.elypia.comcord.constraints.Everyone;
import org.elypia.commandler.event.ActionEvent;

import javax.validation.*;

/**
 * @author seth@elypia.org (Seth Falco)
 */
public class EveryoneValidator implements ConstraintValidator<Everyone, ActionEvent<Event, Message>> {

    @Override
    public boolean isValid(ActionEvent<Event, Message> value, ConstraintValidatorContext context) {
        Event source = value.getRequest().getSource();
        Message message = EventUtils.getMessage(source);

        if (!message.isFromType(ChannelType.TEXT))
            return true;

        TextChannel channel = EventUtils.getTextChannel(source);
        Member member = message.getMember();

        if (member.hasPermission(channel, Permission.MESSAGE_MENTION_EVERYONE))
            return true;

        return message.mentionsEveryone();
    }
}
