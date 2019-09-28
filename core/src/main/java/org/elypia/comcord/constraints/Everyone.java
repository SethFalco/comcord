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
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.elypia.commandler.CommandlerEvent;

import javax.inject.Inject;
import javax.validation.*;
import java.lang.annotation.*;

/**
 * @author seth@elypia.org (Syed Shah)
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {Everyone.Validator.class})
public @interface Everyone {

    String message() default "{org.elypia.comcord.constraints.Everyone.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    class Validator implements ConstraintValidator<Everyone, String> {

        private CommandlerEvent<?, ?> event;

        @Inject
        public Validator(CommandlerEvent<?, ?> event) {
            this.event = event;
        }

        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            MessageReceivedEvent source = (MessageReceivedEvent)event.getSource();

            if (!source.isFromType(ChannelType.TEXT))
                return true;

            TextChannel channel = source.getTextChannel();
            Member member = source.getMessage().getMember();

            if (member.hasPermission(channel, Permission.MESSAGE_MENTION_EVERYONE))
                return true;

            String lower = value.toLowerCase();
            return lower.contains("@everyone") || lower.contains("@here");
        }
    }
}
