package com.elypia.cmdlrdiscord.annotations;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Reaction {

    /**
     * @return The command which response must be reacted to.
     */
    String command();

    /**
     * @return The emotes that can trigger this reaction command.
     */
    String[] emotes();
}
