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

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.events.message.*;
import org.elypia.commandler.CommandlerEvent;

import javax.validation.*;
import java.lang.annotation.*;

/**
 * @author seth@elypia.org (Syed Shah)
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {Owner.Validator.class})
public @interface Owner {

    String message() default "{org.elypia.comcord.constraints.Owner.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    class Validator implements ConstraintValidator<Owner, CommandlerEvent<Event, Message>> {

        @Override
        public boolean isValid(CommandlerEvent<Event, Message> value, ConstraintValidatorContext context) {
            Event source = value.getSource();

            if (source instanceof MessageReceivedEvent) {
                MessageReceivedEvent mre = (MessageReceivedEvent)source;
                return mre.isFromGuild() && mre.getMember().isOwner();
            }

            if (source instanceof MessageUpdateEvent) {
                MessageUpdateEvent mre = (MessageUpdateEvent)source;
                return mre.isFromGuild() && mre.getMember().isOwner();
            }

            return true;
        }
    }
}
