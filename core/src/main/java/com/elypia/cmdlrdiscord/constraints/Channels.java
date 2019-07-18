package com.elypia.cmdlrdiscord.constraints;

import com.elypia.commandler.CommandlerEvent;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.events.message.GenericMessageEvent;

import javax.validation.*;
import java.lang.annotation.*;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {Channels.Validator.class})
public @interface Channels {

    String message() default "{com.elypia.cmdlrdiscord.constraints.Channels.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    ChannelType[] value();

    class Validator implements ConstraintValidator<Channels, CommandlerEvent<?, ?>> {

        private ChannelType[] types;

        @Override
        public void initialize(Channels constraintAnnotation) {
            types = constraintAnnotation.value();
        }

        @Override
        public boolean isValid(CommandlerEvent<?, ?> value, ConstraintValidatorContext context) {
            ChannelType type = ((GenericMessageEvent)value.getSource()).getChannelType();

            for (ChannelType channelType : types) {
                if (channelType == type)
                    return true;
            }

            return false;
        }
    }
}
