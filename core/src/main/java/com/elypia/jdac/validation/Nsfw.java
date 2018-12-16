package com.elypia.jdac.validation;

import com.elypia.jdac.alias.JDACEvent;
import net.dv8tion.jda.core.events.message.GenericMessageEvent;

import javax.validation.*;
import java.lang.annotation.*;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {Nsfw.Validator.class})
public @interface Nsfw {

    String message() default "{com.elypia.jdac.validation.Nsfw.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class Validator implements ConstraintValidator<Nsfw, JDACEvent> {

        @Override
        public boolean isValid(JDACEvent value, ConstraintValidatorContext context) {
            GenericMessageEvent source = value.getSource();
            return !source.getChannelType().isGuild() || source.getTextChannel().isNSFW();
        }
    }
}
