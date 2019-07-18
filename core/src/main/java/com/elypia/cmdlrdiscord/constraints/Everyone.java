package com.elypia.cmdlrdiscord.constraints;

import com.elypia.commandler.CommandlerEvent;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import javax.inject.Inject;
import javax.validation.*;
import java.lang.annotation.*;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {Everyone.Validator.class})
public @interface Everyone {

    String message() default "{com.elypia.cmdlrdiscord.constraints.Everyone.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    class Validator implements ConstraintValidator<Everyone, String> {

        private CommandlerEvent<?, ?> event;

        @Inject
        public Validator(CommandlerEvent<?, ?> event) {
            this.event = event;
        }

        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            MessageReceivedEvent source = (MessageReceivedEvent)event.getSource();

            if (!source.isFromType(ChannelType.TEXT))
                return true;

            TextChannel channel = source.getTextChannel();
            Member member = source.getMessage().getMember();

            if (member.hasPermission(channel, Permission.MESSAGE_MENTION_EVERYONE))
                return true;

            String lower = value.toLowerCase();
            return lower.contains("@everyone") || lower.contains("@here");
        }
    }
}
