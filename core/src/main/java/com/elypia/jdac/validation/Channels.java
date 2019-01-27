package com.elypia.jdac.validation;

import com.elypia.jdac.JDACEvent;
import net.dv8tion.jda.api.entities.ChannelType;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
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
