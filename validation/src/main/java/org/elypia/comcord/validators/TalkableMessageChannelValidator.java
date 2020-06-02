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

import net.dv8tion.jda.api.entities.*;
import org.elypia.comcord.constraints.Talkable;

import javax.validation.*;

/**
 * @author seth@elypia.org (Seth Falco)
 */
public class TalkableMessageChannelValidator implements ConstraintValidator<Talkable, MessageChannel> {

    @Override
    public boolean isValid(MessageChannel channel, ConstraintValidatorContext context) {
        ChannelType type = channel.getType();

        if (!type.isGuild())
            return true;

        TextChannel textChannel = (TextChannel)channel;
        return textChannel.canTalk();
    }
}
