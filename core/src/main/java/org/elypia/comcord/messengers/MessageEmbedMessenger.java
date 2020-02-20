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

import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.*;
import org.elypia.comcord.api.DiscordMessenger;
import org.elypia.commandler.event.ActionEvent;

import javax.inject.Singleton;
import java.util.StringJoiner;

/**
 * @author seth@elypia.org (Seth Falco)
 */
@Singleton
public class MessageEmbedMessenger implements DiscordMessenger<MessageEmbed> {

    @Override
    public Message buildMessage(ActionEvent<?, Message> event, MessageEmbed output) {
        StringJoiner builder = new StringJoiner("\n\n");
        MessageEmbed.AuthorInfo authorInfo = output.getAuthor();
        String title = output.getTitle();
        String description = output.getDescription();
        MessageEmbed.Footer footer = output.getFooter();

        if (authorInfo != null)
            builder.add(authorInfo.getName());

        if (title != null)
            builder.add(title);

        if (description != null)
            builder.add(description);

        if (footer != null)
            builder.add(footer.getText());

        for (MessageEmbed.Field field : output.getFields())
            builder.add(field.getName() + "\n" + field.getValue());

        return new MessageBuilder(builder.toString()).build();
    }

    @Override
    public Message buildEmbed(ActionEvent<?, Message> event, MessageEmbed output) {
        return new MessageBuilder(output).build();
    }
}
