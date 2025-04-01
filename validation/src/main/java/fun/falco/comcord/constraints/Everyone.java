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

package fun.falco.comcord.constraints;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import fun.falco.comcord.validators.EveryoneMessageValidator;

/**
 * Validate that the user has permission to do an @everyone or @here if it's
 * possible for the user to make the bot say @everyone or @here through the
 * parameter provided to it.
 *
 * <p>For commands where the application may use part of the users input in the
 * response of the message. It's important to validate that it's not the user
 * trying to bypass any permissions that prevent them from doing the bulk
 * mentions by making the bot do the bulk mentions for them.</p>
 *
 * @author seth@falco.fun (Seth Falco)
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {EveryoneMessageValidator.class})
public @interface Everyone {

    String message() default "{fun.falco.comcord.constraints.Everyone.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
