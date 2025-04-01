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

import net.dv8tion.jda.api.Permission;
import fun.falco.comcord.validators.cdi.ManagerMessageValidator;

import javax.validation.*;
import java.lang.annotation.*;

/**
 * Validate that the command was performed in a guild, by someone
 * with the {@link Permission#MANAGE_SERVER} permission.
 *
 * @author seth@falco.fun (Seth Falco)
 * @since 2.1.1
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {ManagerMessageValidator.class})
public @interface Manager {

    String message() default "{fun.falco.comcord.constraints.Manager.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
