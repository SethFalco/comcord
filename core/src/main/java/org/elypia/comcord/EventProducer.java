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

package org.elypia.comcord;

import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.guild.GenericGuildEvent;
import net.dv8tion.jda.api.events.guild.member.GenericGuildMemberEvent;
import net.dv8tion.jda.api.events.message.*;
import org.elypia.commandler.event.Request;

import javax.enterprise.context.*;
import javax.enterprise.inject.Produces;

@ApplicationScoped
public class EventProducer {

    private GenericEvent event;

    @RequestScoped
    @Produces
    public GenericEvent getEvent() {
        return event;
    }

    public void setEvent(GenericEvent event) {
        this.event = event;
    }

    /**
     * @param request The Commandler request to get a guild from.
     * @return The member contained within this event.
     */
    @RequestScoped
    @Produces
    public Guild getGuild(Request request) {
        GenericEvent source = (GenericEvent)request.getSource();

        if (source instanceof GenericMessageEvent) {
            GenericMessageEvent genericMessageEvent = (GenericMessageEvent)source;

            if (genericMessageEvent.isFromGuild())
                return genericMessageEvent.getGuild();
        }

        if (source instanceof GenericGuildEvent)
            return ((GenericGuildEvent)source).getGuild();

        return null;
    }

    /**
     * @param request The Commandler request to get a message from.
     * @return The message contained within this event.
     */
    @RequestScoped
    @Produces
    public Message getMessage(Request request) {
        GenericEvent genericEvent = (GenericEvent)request.getSource();

        if (genericEvent instanceof MessageReceivedEvent)
            return ((MessageReceivedEvent)genericEvent).getMessage();

        if (genericEvent instanceof MessageUpdateEvent)
            return ((MessageUpdateEvent)genericEvent).getMessage();

        return null;
    }

    /**
     * @param request The Commandler request to get the User from.
     * @return The member contained within this event.
     */
    @RequestScoped
    @Produces
    public User getAuthor(Request request) {
        GenericEvent genericEvent = (GenericEvent)request.getSource();

        if (genericEvent instanceof MessageReceivedEvent)
            return ((MessageReceivedEvent)genericEvent).getAuthor();

        if (genericEvent instanceof MessageUpdateEvent)
            return ((MessageUpdateEvent)genericEvent).getAuthor();

        return null;
    }
    /**
     * @param request The Commandler request to get a member from.
     * @return The member contained within this event.
     */
    @RequestScoped
    @Produces
    public Member getMember(Request request) {
        GenericEvent genericEvent = (GenericEvent)request.getSource();

        if (genericEvent instanceof MessageReceivedEvent)
            return ((MessageReceivedEvent)genericEvent).getMember();

        if (genericEvent instanceof GenericGuildMemberEvent)
            return ((GenericGuildMemberEvent)genericEvent).getMember();

        return null;
    }

    /**
     * @param request The Commandler request to get a message channel from.
     * @return The message channel contained within this event.
     */
    @RequestScoped
    @Produces
    public MessageChannel getMessageChannel(Request request) {
        GenericEvent genericEvent = (GenericEvent)request.getSource();

        if (genericEvent instanceof GenericMessageEvent)
            return ((GenericMessageEvent)genericEvent).getChannel();

        return null;
    }
}
