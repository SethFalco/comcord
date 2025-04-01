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
import fun.falco.comcord.validators.*;

import javax.validation.*;
import java.lang.annotation.*;

/**
 * Validate that the application has the {@link Permission} required
 * in the channel or event to perform the action.
 *
 * Using the {@link #userNeedsPermission()} parameter, it can also be configured if the
 * user performing the command also required the permissions or not.
 *
 * This is helpful when performing admin related commands, or a command
 * which is essentially a shortcut to several commands in Discord which
 * may require
 *
 * @author seth@falco.fun (Seth Falco)
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {PermissionsMemberValidator.class, PermissionMessageValidator.class})
public @interface Permissions {

    String message() default "{fun.falco.comcord.constraints.Permissions.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    /**
     * @return The permissions required to do this command.
     */
    Permission[] value();

    /**
     * @return If the user also needs these permissions to perform this command.
     */
    boolean userNeedsPermission() default true;
}
