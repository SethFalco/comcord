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

package org.elypia.comcord;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.events.message.GenericMessageEvent;
import org.elypia.commandler.Commandler;
import org.elypia.commandler.api.AbstractIntegration;

import javax.inject.*;
import java.io.Serializable;

/**
 * @author seth@elypia.org (Seth Falco)
 */
@Singleton
public class DiscordIntegration extends AbstractIntegration<Event, Message> {

    @Inject
    public DiscordIntegration(final Commandler commandler, final JDA jda) {
        this.commandler = commandler;
        DiscordConfig config = commandler.getInjector().getInstance(DiscordConfig.class);
        jda.addEventListener(new DiscordListener(this, config));
    }

    @Override
    public Class<Message> getMessageType() {
        return Message.class;
    }

    @Override
    public Serializable getActionId(Event source) {
        if (source instanceof GenericMessageEvent)
            return ((GenericMessageEvent)source).getMessageIdLong();

        throw new IllegalStateException("Can't get serializable ID of this action.");
    }
}
