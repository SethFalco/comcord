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

import org.elypia.comcord.validators.TalkableValidator;

import javax.validation.*;
import java.lang.annotation.*;

/**
 * Validate that the given channel can be communicated in by
 * your application.
 *
 * This is helpful for when you are configure notices, or notifications
 * which may be sent in future and need to validate the channel the user
 * is requesting the message it send is one the application
 * may actually be able to send it.
 *
 * @author seth@elypia.org (Seth Falco)
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {TalkableValidator.class})
public @interface Talkable {

    String message() default "{org.elypia.comcord.constraints.Talkable.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
