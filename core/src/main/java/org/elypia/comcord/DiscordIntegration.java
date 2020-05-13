/*
 * Copyright 2019-2020 Elypia CIC
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
import org.elypia.commandler.api.*;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.Producer;
import javax.inject.Inject;
import java.io.Serializable;

/**
 * @author seth@elypia.org (Seth Falco)
 */
@ApplicationScoped
public class DiscordIntegration implements Integration<Event, Message> {

    private final Commandler commandler;
    private final ActionListener listener;
    private final JDA jda;
    private final DiscordConfig config;

    /**
     * You're expected to use the {@link Producer}
     * annotation to provide the {@link JDA} instance for integration.
     *
     * @param commandler
     * @param listener
     * @param jda
     * @param config
     */
    @Inject
    public DiscordIntegration(Commandler commandler, ActionListener listener, JDA jda, DiscordConfig config) {
        this.commandler = commandler;
        this.listener = listener;
        this.jda = jda;
        this.config = config;

        jda.addEventListener(new DiscordListener(this));
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

    public ActionListener getListener() {
        return listener;
    }

    public DiscordConfig getConfig() {
        return config;
    }
}
