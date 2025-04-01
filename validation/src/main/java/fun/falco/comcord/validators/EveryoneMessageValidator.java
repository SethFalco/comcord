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

package fun.falco.comcord.validators;

import javax.enterprise.context.ApplicationScoped;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import fun.falco.comcord.constraints.Everyone;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

/**
 * @author seth@falco.fun (Seth Falco)
 */
@ApplicationScoped
public class EveryoneMessageValidator implements ConstraintValidator<Everyone, Message> {

    @Override
    public boolean isValid(Message message, ConstraintValidatorContext context) {
        if (!message.isFromGuild()) {
            return true;
        }

        Member member = message.getMember();

        if (member == null) {
            throw new IllegalStateException("Obtained null member from a message in a guild.");
        }

        TextChannel channel = message.getTextChannel();

        if (member.hasPermission(channel, Permission.MESSAGE_MENTION_EVERYONE)) {
            return true;
        }

        return message.mentionsEveryone();
    }
}
