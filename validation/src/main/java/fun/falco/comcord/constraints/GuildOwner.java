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

import fun.falco.comcord.validators.*;

import javax.validation.*;
import java.lang.annotation.*;

/**
 * Validate that a command was performed by the owner of the Guild
 * if the command was performed in a guild at all.
 *
 * If it was not performed in a guild it should always pass.
 *
 * @author seth@falco.fun (Seth Falco)
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {GuildOwnerMemberValidator.class, GuildOwnerMessageValidator.class})
public @interface GuildOwner {

    String message() default "{fun.falco.comcord.constraints.GuildOwner.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
