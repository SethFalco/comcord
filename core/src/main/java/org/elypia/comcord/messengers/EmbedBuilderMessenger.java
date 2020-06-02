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

package org.elypia.comcord.messengers;

import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.Event;
import org.elypia.comcord.EventUtils;
import org.elypia.comcord.api.DiscordMessenger;
import org.elypia.commandler.Commandler;
import org.elypia.commandler.annotation.stereotypes.MessageProvider;
import org.elypia.commandler.api.Messenger;
import org.elypia.commandler.event.ActionEvent;

import java.util.StringJoiner;

/**
 * @author seth@elypia.org (Seth Falco)
 */
@MessageProvider(provides = Message.class, value = {EmbedBuilder.class})
public class EmbedBuilderMessenger implements DiscordMessenger<EmbedBuilder> {

    /**
     * <strong>
     *     It's not a good idea to rely on this, it's preferred
     *     to return a custom data object and implement either a {@link Messenger}
     *     for it if it will only build string messages, or the {@link DiscordMessenger}
     *     if it may build messages or embeds. Alternatively build the {@link MessageEmbed}
     *     instead so that the {@link MessageEmbedMessenger} can access more information
     *     when converting it into a string message for you.
     * </strong>
     *
     * @param event The {@link Commandler} event object.
     * @param output The message that needs to be represented at text for the user.
     * @return The message to send to the user.
     */
    @Override
    public Message buildMessage(ActionEvent<?, Message> event, EmbedBuilder output) {
        StringJoiner builder = new StringJoiner("\n\n");
        StringBuilder description = output.getDescriptionBuilder();

        if (description.length() > 0)
            builder.add(description.toString());

        for (MessageEmbed.Field field : output.getFields())
            builder.add(field.getName() + "\n" + field.getValue());

        return new MessageBuilder(builder.toString()).build();
    }

    @Override
    public Message buildEmbed(ActionEvent<?, Message> event, EmbedBuilder output) {
        Event source = (Event)event.getRequest().getSource();
        Guild guild = EventUtils.getGuild(source);

        if (guild != null)
            output.setColor(guild.getSelfMember().getColor());

        return new MessageBuilder(output.build()).build();
    }
}
