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

import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.events.guild.GenericGuildEvent;
import net.dv8tion.jda.api.events.guild.member.GenericGuildMemberEvent;
import net.dv8tion.jda.api.events.message.*;

/**
 * Obtain event entities from various types
 * of events.
 *
 * @author seth@elypia.org (Seth Falco)
 */
public final class EventUtils {

    private EventUtils() {
        // Do nothing
    }

    /**
     * @param source The Discord event to get a member from.
     * @return The member contained within this event.
     */
    public static Member getMember(final Event source) {
        if (source instanceof MessageReceivedEvent)
            return ((MessageReceivedEvent)source).getMember();

        if (source instanceof GenericGuildMemberEvent)
            return ((GenericGuildMemberEvent)source).getMember();

        return null;
    }

    /**
     * @param source The Discord event to get a author from.
     * @return The member contained within this event.
     */
    public static User getAuthor(final Event source) {
        if (source instanceof MessageReceivedEvent)
            return ((MessageReceivedEvent)source).getAuthor();

        if (source instanceof MessageUpdateEvent)
            return ((MessageUpdateEvent)source).getAuthor();

        return null;
    }

    /**
     * @param source The Discord event to get a message from.
     * @return The member contained within this event.
     */
    public static Message getMessage(final Event source) {
        if (source instanceof MessageReceivedEvent)
            return ((MessageReceivedEvent)source).getMessage();

        if (source instanceof MessageUpdateEvent)
            return ((MessageUpdateEvent)source).getMessage();

        return null;
    }

    /**
     * @param source The Discord event to get a guild from.
     * @return The member contained within this event.
     */
    public static Guild getGuild(final Event source) {
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
     * @param source The Discord event to get a text channel from.
     * @return The text channel contained within this event.
     */
    public static TextChannel getTextChannel(final Event source) {
        if (source instanceof GenericMessageEvent)
            return ((GenericMessageEvent)source).getTextChannel();

        return null;
    }

    /**
     * @param source The Discord event to get a message channel from.
     * @return The message channel contained within this event.
     */
    public static MessageChannel getMessageChannel(final Event source) {
        if (source instanceof GenericMessageEvent)
            return ((GenericMessageEvent)source).getChannel();

        return null;
    }
}
