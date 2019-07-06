package com.elypia.cmdlrdiscord;

public enum Scope {

    /** Scoped everything that the bot can see. */
    GLOBAL,

    /**
     * Scoped mutual places that the performing user, and the bot
     * can both see.
     */
    MUTUAL,

    /**
     * Only search in the current group or channel.
     */
    LOCAL
}
