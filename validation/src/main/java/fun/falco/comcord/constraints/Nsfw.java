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

import fun.falco.comcord.validators.NsfwMessageChannelValidator;

import javax.validation.*;
import java.lang.annotation.*;

/**
 * Verify that the application is allowed to send things deemed
 * NSFW in the channel.
 *
 * If the message is in PMs/DMs, it should allow the message regardless.
 *
 * @author seth@falco.fun (Seth Falco)
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {NsfwMessageChannelValidator.class})
public @interface Nsfw {

    String message() default "{fun.falco.comcord.constraints.Nsfw.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
