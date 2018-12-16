package com.elypia.jdac.validation;

import com.elypia.jdac.alias.JDACEvent;
import net.dv8tion.jda.core.entities.ChannelType;

import javax.validation.*;
import java.lang.annotation.*;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {Channels.Validator.class})
public @interface Channels {

    ChannelType[] value();

    String message() default "{com.elypia.jdac.validation.Channels.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class Validator implements ConstraintValidator<Channels, JDACEvent> {
        private ChannelType[] types;

        @Override
        public void initialize(Channels constraintAnnotation) {
            types = constraintAnnotation.value();
        }

        @Override
        public boolean isValid(JDACEvent value, ConstraintValidatorContext context) {
            ChannelType type = value.getSource().getChannelType();

            for (ChannelType channelType : types) {
                if (channelType == type)
                    return true;
            }

            return false;
        }
    }
}
