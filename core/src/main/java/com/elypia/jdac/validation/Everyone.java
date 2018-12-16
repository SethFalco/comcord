package com.elypia.jdac.validation;

import com.elypia.jdac.alias.*;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import javax.validation.*;
import java.lang.annotation.*;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {Everyone.Validator.class})
public @interface Everyone {

    String message() default "{com.elypia.jdac.validation.Everyone.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class Validator extends JDACConstraintValidator<Everyone, String> {

        public Validator(JDACEvent event) {
            super(event);
        }

        @Override
        public boolean isValid(JDACEvent event, String value, ConstraintValidatorContext context) {
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
