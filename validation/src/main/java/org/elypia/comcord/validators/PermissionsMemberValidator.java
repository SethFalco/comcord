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

package org.elypia.comcord.validators;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import org.elypia.comcord.constraints.Permissions;

import javax.enterprise.context.ApplicationScoped;
import javax.validation.*;

/**
 * @author seth@falco.fun (Seth Falco)
 */
@ApplicationScoped
public class PermissionsMemberValidator implements ConstraintValidator<Permissions, Member> {

    private Permission[] permissions;
    private boolean userNeedsPermission;

    @Override
    public void initialize(Permissions constraintAnnotation) {
        permissions = constraintAnnotation.value();
        userNeedsPermission = constraintAnnotation.userNeedsPermission();
    }

    @Override
    public boolean isValid(Member member, ConstraintValidatorContext context) {
        Guild guild = member.getGuild();

        if (!guild.getSelfMember().hasPermission(permissions))
            return false;

        if (!userNeedsPermission)
            return true;

        return member.hasPermission(permissions);
    }
}
