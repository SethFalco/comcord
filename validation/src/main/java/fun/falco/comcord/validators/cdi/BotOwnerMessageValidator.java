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

import java.time.Instant;
import java.util.concurrent.CompletableFuture;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fun.falco.comcord.constraints.BotOwner;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.ApplicationInfo;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;

/**
 * @author seth@falco.fun (Seth Falco)
 */
@ApplicationScoped
public class BotOwnerMessageValidator implements ConstraintValidator<BotOwner, Message> {

    private static final Logger logger = LoggerFactory.getLogger(BotOwnerMessageValidator.class);

    private final CompletableFuture<ApplicationInfo> future;
    private Long ownerId;

    @Inject
    public BotOwnerMessageValidator(JDA jda) {
        future = jda.retrieveApplicationInfo().submit();
    }

    @Override
    public void initialize(BotOwner elevated) {
        try {
            ownerId = future.get().getOwner().getIdLong();
        } catch (Exception ex) {
            logger.error("Failed to obtain application owner's Discord ID, will be invalid if it's the owner.", ex);
        }
    }

    @Override
    public boolean isValid(Message message, ConstraintValidatorContext context) {
        if (ownerId == null) {
            return false;
        }

        User user = message.getAuthor();
        boolean isValid = user.getIdLong() == ownerId;
        logger.info("A command reserved for bot owners was attempted at {} by the user {}, returned {}.", Instant.now(), user, isValid);
        return isValid;
    }
}
