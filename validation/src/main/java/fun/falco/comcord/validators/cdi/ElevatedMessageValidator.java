/*
 * Copyright 2019-2025 Seth Falco and Comcord Contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package fun.falco.comcord.validators.cdi;

import java.util.concurrent.CompletableFuture;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fun.falco.comcord.constraints.Elevated;
import fun.falco.comcord.constraints.Permissions;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.ApplicationInfo;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

/**
 * Not intended for checking specific permissions, but rather as a generic way
 * to determine if a user is allowed to configure parts of the bot.
 *
 * <p>See {@link Permissions} for checking permissions.</p>
 *
 * @author seth@falco.fun (Seth Falco)
 */
@ApplicationScoped
public class ElevatedMessageValidator implements ConstraintValidator<Elevated, Message> {

    private static final Logger logger = LoggerFactory.getLogger(ElevatedMessageValidator.class);

    private final CompletableFuture<ApplicationInfo> future;
    private Long ownerId;

    @Inject
    public ElevatedMessageValidator(JDA jda) {
        future = jda.retrieveApplicationInfo().submit();
    }

    @Override
    public void initialize(Elevated elevated) {
        try {
            ownerId = future.get().getOwner().getIdLong();
        } catch (Exception ex) {
            logger.error("Failed to obtain application owner's Discord ID, will be invalid if it's the owner.", ex);
        }
    }

    @Override
    public boolean isValid(Message message, ConstraintValidatorContext context) {
        if (!message.isFromGuild()) {
            return true;
        }

        Member member = message.getMember();

        if (member == null) {
            throw new IllegalStateException("Non-null message and non-null guild returned null member.");
        }

        TextChannel channel = message.getTextChannel();

        if (member.hasPermission(channel, Permission.MANAGE_SERVER)) {
            return true;
        }

        if (ownerId == null) {
            return false;
        }

        User user = member.getUser();
        return user.getIdLong() == ownerId;
    }
}
