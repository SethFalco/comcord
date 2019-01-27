package com.elypia.jdac.validation;

import com.elypia.jdac.JDACEvent;
import net.dv8tion.jda.api.events.message.GenericMessageEvent;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
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
