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

import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.entities.*;
import fun.falco.comcord.constraints.*;
import org.slf4j.*;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.*;

import java.util.concurrent.CompletableFuture;

/**
 * This is the same as the {@link ElevatedMessageValidator}
 * except it rejects messages in {@link ChannelType#PRIVATE} channels.
 *
 * See {@link fun.falco.comcord.constraints.Permissions} for checking permissions.
 *
 * @author seth@falco.fun (Seth Falco)
 */
@ApplicationScoped
public class ManagerMessageValidator implements ConstraintValidator<Manager, Message> {

    private static final Logger logger = LoggerFactory.getLogger(ManagerMessageValidator.class);

    private final CompletableFuture<ApplicationInfo> future;
    private Long ownerId;

    @Inject
    public ManagerMessageValidator(JDA jda) {
        future = jda.retrieveApplicationInfo().submit();
    }

    @Override
    public void initialize(Manager elevated) {
        try {
            ownerId = future.get().getOwner().getIdLong();
        } catch (Exception ex) {
            logger.error("Failed to obtain application owner's Discord ID, will be invalid if it's the owner.", ex);
        }
    }

    @Override
    public boolean isValid(Message message, ConstraintValidatorContext context) {
        if (!message.isFromGuild())
            return false;

        Member member = message.getMember();

        if (member == null)
            throw new IllegalStateException("Non-null message and non-null guild returned null member.");

        TextChannel channel = message.getTextChannel();

        if (member.hasPermission(channel, Permission.MANAGE_SERVER))
            return true;

        if (ownerId == null)
            return false;

        User user = member.getUser();
        return user.getIdLong() == ownerId;
    }
}
