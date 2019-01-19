package com.elypia.jdac.validation;

import com.elypia.jdac.alias.JDACEvent;
import net.dv8tion.jda.api.entities.ApplicationInfo;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import javax.validation.*;
import java.lang.annotation.*;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {Developer.Validator.class})
public @interface Developer {

    String message() default "{com.elypia.jdac.validation.Developer.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class Validator implements ConstraintValidator<Developer, JDACEvent> {

        private static long ownerId;

        @Override
        public boolean isValid(JDACEvent value, ConstraintValidatorContext context) {
            MessageReceivedEvent source = (MessageReceivedEvent)value.getSource();

            if (ownerId == 0) {
                ApplicationInfo info = value.getSource().getJDA().getApplicationInfo().complete();
                ownerId = info.getOwner().getIdLong();
            }

            return source.getAuthor().getIdLong() == ownerId;
        }
    }
}
