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

package fun.falco.comcord;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.Producer;
import javax.inject.Inject;

import org.elypia.commandler.api.ActionListener;
import org.elypia.commandler.api.Integration;
import org.elypia.commandler.event.ActionEvent;

import fun.falco.comcord.configuration.ComcordConfig;
import fun.falco.comcord.configuration.DiscordConfig;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.GenericMessageEvent;

/**
 * @author seth@falco.fun (Seth Falco)
 */
@ApplicationScoped
public class DiscordIntegration implements Integration<GenericEvent, Message> {

    private final ActionListener listener;
    private final DiscordConfig discordConfig;
    private final ComcordConfig comcordConfig;
    private final JDA jda;

    /**
     * You're expected to use the {@link Producer}
     * annotation to provide the {@link JDA} instance for integration.
     *
     * @param listener Commandler action listener implementation to send commands to.
     * @param discordConfig Discord configuration.
     * @param jda Discord API client.
     */
    @Inject
    public DiscordIntegration(ActionListener listener, DiscordConfig discordConfig, ComcordConfig comcordConfig, JDA jda) {
        this.listener = listener;
        this.discordConfig = discordConfig;
        this.comcordConfig = comcordConfig;
        this.jda = jda;
    }

    @Override
    public void init() {
        jda.addEventListener(new DiscordListener(this));
    }

    @Override
    public Class<Message> getMessageType() {
        return Message.class;
    }

    @Override
    public Serializable getActionId(GenericEvent source) {
        if (source instanceof GenericMessageEvent) {
            return ((GenericMessageEvent) source).getMessageIdLong();
        }

        throw new IllegalStateException("Can't get serializable ID of this action.");
    }

    @Override
    public void send(ActionEvent<GenericEvent, Message> event, Message response) {
        MessageChannel channel = EventUtils.getMessageChannel(event.getRequest().getSource());
        channel.sendMessage(response).queue();
    }

    public ActionListener getListener() {
        return listener;
    }

    public DiscordConfig getDiscordConfig() {
        return discordConfig;
    }

    public ComcordConfig getComcordConfig() {
        return comcordConfig;
    }

    public JDA getJda() {
        return jda;
    }
}
