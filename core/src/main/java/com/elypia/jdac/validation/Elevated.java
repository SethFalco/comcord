package com.elypia.jdac.validation;

import com.elypia.jdac.alias.JDACEvent;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import javax.validation.*;
import java.lang.annotation.*;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {Elevated.Validator.class})
public @interface Elevated {

    String message() default "{com.elypia.jdac.validation.Achievements.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class Validator implements ConstraintValidator<Elevated, JDACEvent> {

        @Override
        public boolean isValid(JDACEvent value, ConstraintValidatorContext context) {
            MessageReceivedEvent source = (MessageReceivedEvent)value.getSource();

            if (!source.getChannelType().isGuild())
                return true;

            TextChannel channel = source.getTextChannel();

            if (source.getMember().hasPermission(channel, Permission.MANAGE_SERVER))
                return true;

            return new Developer.Validator().isValid(value, context);
        }
    }
}
