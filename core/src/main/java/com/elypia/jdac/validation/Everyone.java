package com.elypia.jdac.validation;

import com.elypia.jdac.JDACConstraintValidator;
import com.elypia.jdac.JDACEvent;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import javax.validation.Constraint;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
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
