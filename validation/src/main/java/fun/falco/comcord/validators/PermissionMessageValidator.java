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

import fun.falco.comcord.constraints.Permissions;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

/**
 * @author seth@falco.fun (Seth Falco)
 */
@ApplicationScoped
public class PermissionMessageValidator implements ConstraintValidator<Permissions, Message> {

    private Permission[] permissions;
    private boolean userNeedsPermission;

    @Override
    public void initialize(Permissions constraintAnnotation) {
        permissions = constraintAnnotation.value();
        userNeedsPermission = constraintAnnotation.userNeedsPermission();
    }

    @Override
    public boolean isValid(Message message, ConstraintValidatorContext context) {
        if (!message.isFromGuild()) {
            throw new IllegalStateException("Permission validation check can not be used in DMs.");
        }

        Guild guild = message.getGuild();
        TextChannel channel = message.getTextChannel();

        if (!guild.getSelfMember().hasPermission(channel, permissions)) {
            return false;
        }

        if (!userNeedsPermission) {
            return true;
        }

        Member member = message.getMember();

        if (member == null) {
            throw new IllegalStateException("Non-null message with non-null guild returned null author.");
        }

        return member.hasPermission(channel, permissions);
    }
}
