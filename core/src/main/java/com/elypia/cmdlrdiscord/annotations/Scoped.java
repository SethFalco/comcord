package com.elypia.cmdlrdiscord.annotations;

import com.elypia.cmdlrdiscord.Scope;

import java.lang.annotation.*;

/**
 * Some objects in Discord can be searched but there are
 * various scopes to search from. For example searching a User
 * could be searched Locally, in the current chat only, Mutually
 * only in the current chat or in any mutual guilds, and Globally,
 * through all of Discord that the bot can see.
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

