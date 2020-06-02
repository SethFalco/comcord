/*
 * Copyright 2019-2019 Elypia CIC
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

package org.elypia.comcord.validators.cdi;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import org.elypia.comcord.constraints.BotOwner;
import org.slf4j.*;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.*;
import java.time.Instant;

/**
 * @author seth@elypia.org (Seth Falco)
 */
@ApplicationScoped
public class BotOwnerMessageValidator implements ConstraintValidator<BotOwner, Message> {

    private static final Logger logger = LoggerFactory.getLogger(BotOwnerMessageValidator.class);

    private long ownerId;

    @Inject
    public BotOwnerMessageValidator(JDA jda) {
        ApplicationInfo info = jda.retrieveApplicationInfo().complete();
        ownerId = info.getOwner().getIdLong();
    }

    @Override
    public boolean isValid(Message message, ConstraintValidatorContext context) {
        User user = message.getAuthor();
        boolean isValid = user.getIdLong() == ownerId;

        logger.info("A command reserved for bot owners was attemped at {} by the user {}, returned {}.", Instant.now(), user, isValid);

        return isValid;
    }
}
