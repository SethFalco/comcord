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

import org.elypia.comcord.dispatchers.ReactionDispatcher;
import org.elypia.commandler.annotation.*;

import java.lang.annotation.*;

@Command
@PropertyWrapper(type = ReactionDispatcher.class)
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface ReactionCommand {

    /**
     * @return The string representation of the
     * emoji that triggers the specified command.
     */
    @Property(key = "emote")
    String emote();

    /**
     * @return The controller type this calls
     * to, defaults to the controller that got
     * reacted to.
     */
    @Property(key = "controller")
    Class<?> controller() default ReactionCommand.class;

    /**
     * @return The name of the method this calls,
     * defaults to the method of the command that
     * got reacted to.
     */
    @Property(key = "command")
    String command() default AnnotationUtils.EFFECTIVELY_NULL;

    /**
     * Use $0, or $1 to specify a particular argument in the command.
     * Or specify ${...} to use Java Expression Language, if neither
     * is used, the string as accepted literally.
     *
     * @return An array of parameters, this supports
     * Java Expression Language and so should provide
     * a lot of capabilities.
     */
    @Property(key = "params")
    String params() default AnnotationUtils.EFFECTIVELY_NULL;
}
