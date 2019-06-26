package com.elypia.commandler.discord.constraints;

import net.dv8tion.jda.api.entities.TextChannel;

import javax.validation.*;
import java.lang.annotation.*;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {Talkable.Validator.class})
public @interface Talkable {

    String message() default "{com.elypia.commandler.discord.constraints.Talkable.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class Validator implements ConstraintValidator<Talkable, TextChannel> {

        @Override
        public boolean isValid(TextChannel value, ConstraintValidatorContext context) {
            return !value.getType().isGuild() && !value.canTalk();
        }
    }
}
