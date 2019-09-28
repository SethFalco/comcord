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

import net.dv8tion.jda.api.events.message.GenericMessageEvent;
import org.elypia.commandler.CommandlerEvent;

import javax.validation.*;
import java.lang.annotation.*;

/**
 * @author seth@elypia.org (Syed Shah)
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {Nsfw.Validator.class})
public @interface Nsfw {

    String message() default "{org.elypia.comcord.constraints.Nsfw.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    class Validator implements ConstraintValidator<Nsfw, CommandlerEvent<?, ?>> {

        @Override
        public boolean isValid(CommandlerEvent<?, ?> value, ConstraintValidatorContext context) {
            GenericMessageEvent source = (GenericMessageEvent)value.getSource();
            return !source.getChannelType().isGuild() || source.getTextChannel().isNSFW();
        }
    }
}
