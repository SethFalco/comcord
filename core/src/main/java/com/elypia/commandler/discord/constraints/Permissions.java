package com.elypia.commandler.discord.constraints;

import com.elypia.commandler.CommandlerEvent;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import javax.validation.*;
import java.lang.annotation.*;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {Permissions.Validator.class})
public @interface Permissions {

    String message() default "{com.elypia.commandler.discord.constraints.Permissions.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    /**
     * @return The permissions required to do this command.
     */
    Permission[] value();

    /**
     * @return If the user also needs these permissions to perform this command.
     */
    boolean user() default true;

    class Validator implements ConstraintValidator<Permissions, CommandlerEvent<?>> {

        private Permission[] permissions;
        private boolean user;

        @Override
        public void initialize(Permissions constraintAnnotation) {
            permissions = constraintAnnotation.value();
            user = constraintAnnotation.user();
        }

        @Override
        public boolean isValid(CommandlerEvent<?> value, ConstraintValidatorContext context) {
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
