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
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.events.guild.GenericGuildEvent;
import net.dv8tion.jda.api.events.guild.member.GenericGuildMemberEvent;
import net.dv8tion.jda.api.events.message.*;
import org.elypia.commandler.api.*;
import org.elypia.commandler.event.*;

import javax.enterprise.context.*;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.Producer;
import javax.inject.Inject;
import java.io.Serializable;

/**
 * @author seth@elypia.org (Seth Falco)
 */
@ApplicationScoped
public class DiscordIntegration implements Integration<Event, Message> {

    private final ActionListener listener;
    private final DiscordConfig discordConfig;
    private final ComcordConfig comcordConfig;
    private final JDA jda;

    /**
     * You're expected to use the {@link Producer}
     * annotation to provide the {@link JDA} instance for integration.
     *
     * @param listener Commandlers action listener implementation to send commands to.
     * @param discordConfig The discord configuration.
     * @param jda The Discord API client.
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
    public Serializable getActionId(Event source) {
        if (source instanceof GenericMessageEvent)
            return ((GenericMessageEvent)source).getMessageIdLong();

        throw new IllegalStateException("Can't get serializable ID of this action.");
    }

    @Override
    public void send(ActionEvent<Event, Message> event, Message response) {
        MessageChannel channel = getMessageChannel(event.getRequest());
        channel.sendMessage(response).queue();
    }

    /**
     * @param request The Commandler request to get a guild from.
     * @return The member contained within this event.
     */
    @RequestScoped
    @Produces
    public Guild getGuild(Request request) {
        Event source = (Event)request.getSource();

        if (source instanceof GenericMessageEvent) {
            GenericMessageEvent event = (GenericMessageEvent)source;

            if (event.isFromGuild())
                return event.getGuild();
        }

        if (source instanceof GenericGuildEvent)
            return ((GenericGuildEvent)source).getGuild();

        return null;
    }

    /**
     * @param request The Commandler request to get a text channel from.
     * @return The text channel contained within this event.
     */
    @RequestScoped
    @Produces
    public TextChannel getTextChannel(Request request) {
        Event source = (Event)request.getSource();

        if (source instanceof GenericMessageEvent)
            return ((GenericMessageEvent)source).getTextChannel();

        return null;
    }

    /**
     * @param request The Commandler request to get a message from.
     * @return The message contained within this event.
     */
    @RequestScoped
    @Produces
    public Message getMessage(Request request) {
        Event source = (Event)request.getSource();

        if (source instanceof MessageReceivedEvent)
            return ((MessageReceivedEvent)source).getMessage();

        if (source instanceof MessageUpdateEvent)
            return ((MessageUpdateEvent)source).getMessage();

        return null;
    }

    /**
     * @param request The Commandler request to get the User from.
     * @return The member contained within this event.
     */
    @RequestScoped
    @Produces
    public User getAuthor(Request request) {
        Event source = (Event)request.getSource();

        if (source instanceof MessageReceivedEvent)
            return ((MessageReceivedEvent)source).getAuthor();

        if (source instanceof MessageUpdateEvent)
            return ((MessageUpdateEvent)source).getAuthor();

        return null;
    }
    /**
     * @param request The Commandler request to get a member from.
     * @return The member contained within this event.
     */
    @RequestScoped
    @Produces
    public static Member getMember(Request request) {
        Event source = (Event)request.getSource();

        if (source instanceof MessageReceivedEvent)
            return ((MessageReceivedEvent)source).getMember();

        if (source instanceof GenericGuildMemberEvent)
            return ((GenericGuildMemberEvent)source).getMember();

        return null;
    }

    /**
     * @param request The Commandler request to get a message channel from.
     * @return The message channel contained within this event.
     */
    @RequestScoped
    @Produces
    public static MessageChannel getMessageChannel(Request request) {
        Event source = (Event)request.getSource();

        if (source instanceof GenericMessageEvent)
            return ((GenericMessageEvent)source).getChannel();

        return null;
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
