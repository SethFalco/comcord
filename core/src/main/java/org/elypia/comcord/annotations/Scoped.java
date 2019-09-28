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

package org.elypia.comcord.annotations;

import org.elypia.comcord.Scope;

import java.lang.annotation.*;

/**
 * Some objects in Discord can be searched but there are
 * various scopes to search from. For example searching a User
 * could be searched Locally, in the current chat only, Mutually
 * only in the current chat or in any mutual guilds, and Globally,
 * through all of Discord that the bot can see.
 *
 * @author seth@elypia.org (Syed Shah)
 */
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Scoped {

    /**
     * @return The scope to search by in a guild.
     */
    Scope inGuild() default Scope.LOCAL;

    /**
     * @return The scope to search by when not in a guild.
     */
    Scope inPrivate() default Scope.MUTUAL;
}

