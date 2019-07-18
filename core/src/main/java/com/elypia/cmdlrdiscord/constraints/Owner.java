package com.elypia.cmdlrdiscord.constraints;

import com.elypia.commandler.CommandlerEvent;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.events.message.*;

import javax.validation.*;
import java.lang.annotation.*;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {Owner.Validator.class})
public @interface Owner {

    String message() default "{com.elypia.cmdlrdiscord.constraints.Owner.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    class Validator implements ConstraintValidator<Owner, CommandlerEvent<Event, Message>> {

        @Override
        public boolean isValid(CommandlerEvent<Event, Message> value, ConstraintValidatorContext context) {
            Event source = value.getSource();

            if (source instanceof MessageReceivedEvent) {
                MessageReceivedEvent mre = (MessageReceivedEvent)source;
                return mre.isFromGuild() && mre.getMember().isOwner();
            }

            if (source instanceof MessageUpdateEvent) {
                MessageUpdateEvent mre = (MessageUpdateEvent)source;
                return mre.isFromGuild() && mre.getMember().isOwner();
            }

            return true;
        }
    }
}
