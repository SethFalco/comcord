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

package org.elypia.comcord.constraints;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.elypia.commandler.CommandlerEvent;

import javax.validation.*;
import java.lang.annotation.*;

/**
 * @author seth@elypia.org (Syed Shah)
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {Permissions.Validator.class})
public @interface Permissions {

    String message() default "{org.elypia.comcord.constraints.Permissions.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    /**
     * @return The permissions required to do this command.
     */
    Permission[] value();

    /**
     * @return If the user also needs these permissions to perform this command.
     */
    boolean user() default true;

    class Validator implements ConstraintValidator<Permissions, CommandlerEvent<?, ?>> {

        private Permission[] permissions;
        private boolean user;

        @Override
        public void initialize(Permissions constraintAnnotation) {
            permissions = constraintAnnotation.value();
            user = constraintAnnotation.user();
        }

        @Override
        public boolean isValid(CommandlerEvent<?, ?> value, ConstraintValidatorContext context) {
            MessageReceivedEvent source = (MessageReceivedEvent)value.getSource();

            if (!source.getChannelType().isGuild())
                return true;

            TextChannel channel = source.getTextChannel();

            if (!source.getGuild().getSelfMember().hasPermission(channel, permissions))
                return false;

            return !user || source.getMember().hasPermission(channel, permissions);
        }
    }
}
