package com.elypia.commandler.discord.constraints;

import com.elypia.commandler.CommandlerEvent;
import net.dv8tion.jda.api.entities.ApplicationInfo;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import javax.validation.*;
import java.lang.annotation.*;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {Developer.Validator.class})
public @interface Developer {

    String message() default "{com.elypia.commandler.discord.constraints.Developer.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    class Validator implements ConstraintValidator<Developer, CommandlerEvent<?>> {

        private static long ownerId;

        @Override
        public boolean isValid(CommandlerEvent<?> value, ConstraintValidatorContext context) {
            MessageReceivedEvent source = (MessageReceivedEvent)value.getSource();

            if (ownerId == 0) {
                ApplicationInfo info = source.getJDA().retrieveApplicationInfo().complete();
                ownerId = info.getOwner().getIdLong();
            }

            return source.getAuthor().getIdLong() == ownerId;
        }
    }
}
