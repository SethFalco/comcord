package com.elypia.jdac.validation;

import com.elypia.jdac.alias.JDACEvent;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import javax.validation.*;
import java.lang.annotation.*;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {Permissions.Validator.class})
public @interface Permissions {

    Permission[] value();

    boolean user() default true;

    String message() default "{com.elypia.jdac.validation.Permissions.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class Validator implements ConstraintValidator<Permissions, JDACEvent> {

        private Permission[] permissions;
        private boolean user;

        @Override
        public void initialize(Permissions constraintAnnotation) {
            permissions = constraintAnnotation.value();
            user = constraintAnnotation.user();
        }

        @Override
        public boolean isValid(JDACEvent value, ConstraintValidatorContext context) {
            MessageReceivedEvent source = (MessageReceivedEvent)value.getSource();

            if (!source.getChannelType().isGuild())
                return true;

            TextChannel channel = source.getTextChannel();

            if (!source.getGuild().getSelfMember().hasPermission(channel, permissions))
                return false;

            return !user || source.getMember().hasPermission(channel, permissions);
        }
    }
}
