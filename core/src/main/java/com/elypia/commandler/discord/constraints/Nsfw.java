package com.elypia.commandler.discord.constraints;

import com.elypia.commandler.CommandlerEvent;
import net.dv8tion.jda.api.events.message.GenericMessageEvent;

import javax.validation.*;
import java.lang.annotation.*;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {Nsfw.Validator.class})
public @interface Nsfw {

    String message() default "{com.elypia.commandler.discord.constraints.Nsfw.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    class Validator implements ConstraintValidator<Nsfw, CommandlerEvent<?>> {

        @Override
        public boolean isValid(CommandlerEvent<?> value, ConstraintValidatorContext context) {
            GenericMessageEvent source = (GenericMessageEvent)value.getSource();
            return !source.getChannelType().isGuild() || source.getTextChannel().isNSFW();
        }
    }
}
