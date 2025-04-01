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

package fun.falco.comcord.messengers;

import java.util.StringJoiner;

import org.elypia.commandler.Commandler;
import org.elypia.commandler.annotation.stereotypes.MessageProvider;
import org.elypia.commandler.api.Messenger;
import org.elypia.commandler.event.ActionEvent;

import fun.falco.comcord.EventUtils;
import fun.falco.comcord.api.DiscordMessenger;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.GenericEvent;

/**
 * @author seth@falco.fun (Seth Falco)
 */
@MessageProvider(provides = Message.class, value = {EmbedBuilder.class})
public class EmbedBuilderMessenger implements DiscordMessenger<EmbedBuilder> {

    /**
     * It's a bad idea to rely on this. You should return a custom data-type and
     * implement either a {@link Messenger} for it, if it will only build string
     * messages, or the {@link DiscordMessenger} if it may build messages or
     * embeds. Alternatively build the {@link MessageEmbed} instead so that the
     * {@link MessageEmbedMessenger} can access more information when converting
     * it into a string message for you.
     *
     * @param event {@link Commandler} event object.
     * @param output Message that needs to be represented at text for the user.
     * @return Message to send to the user.
     */
    @Override
    public Message buildMessage(ActionEvent<?, Message> event, EmbedBuilder output) {
        StringJoiner builder = new StringJoiner("\n\n");
        StringBuilder description = output.getDescriptionBuilder();

        if (description.length() > 0) {
            builder.add(description.toString());
        }

        for (MessageEmbed.Field field : output.getFields()) {
            builder.add(field.getName() + "\n" + field.getValue());
        }

        return new MessageBuilder(builder.toString()).build();
    }

    @Override
    public Message buildEmbed(ActionEvent<?, Message> event, EmbedBuilder output) {
        GenericEvent source = (GenericEvent) event.getRequest().getSource();
        Guild guild = EventUtils.getGuild(source);

        if (guild != null) {
            output.setColor(guild.getSelfMember().getColor());
        }

        return new MessageBuilder(output.build()).build();
    }
}
