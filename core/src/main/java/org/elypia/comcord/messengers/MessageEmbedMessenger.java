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

package org.elypia.comcord.messengers;

import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.entities.*;
import org.elypia.comcord.api.DiscordMessenger;
import org.elypia.commandler.annotation.stereotypes.MessageProvider;
import org.elypia.commandler.api.Messenger;
import org.elypia.commandler.event.ActionEvent;
import org.slf4j.*;

import java.util.StringJoiner;

/**
 * <strong>
 *     Really you should not depend on this {@link Messenger}
 *     as it can omit information and produce unreliable and
 *     unexpected string messages if the bot doesn't have the
 *     {@link Permission#MESSAGE_EMBED_LINKS} permission.
 *
 *     You should prefer to handle working around permissions
 *     yourself and return a {@link Message}, or alternatively
 *     return a custom model object and implement a {@link DiscordMessenger}
 *     for it to handle how to build a message with an embed, vs
 *     as just text.
 * </strong>
 * @author seth@falco.fun (Seth Falco)
 */
@MessageProvider(provides = Message.class, value = MessageEmbed.class)
public class MessageEmbedMessenger implements DiscordMessenger<MessageEmbed> {

    private static final Logger logger = LoggerFactory.getLogger(MessageEmbedMessenger.class);

    @Override
    public Message buildMessage(ActionEvent<?, Message> event, MessageEmbed messageEmbed) {
        logger.warn("Missing {} permission! Converting MessageEmbed to String before sending. Consider using a model object and custom {} implementation.", Permission.MESSAGE_EMBED_LINKS, DiscordMessenger.class);

        StringJoiner joiner = new StringJoiner("\n\n");
        MessageEmbed.AuthorInfo authorInfo = messageEmbed.getAuthor();
        String title = messageEmbed.getTitle();
        String description = messageEmbed.getDescription();
        MessageEmbed.ImageInfo image = messageEmbed.getImage();
        MessageEmbed.Thumbnail thumbnail = messageEmbed.getThumbnail();
        MessageEmbed.Footer footer = messageEmbed.getFooter();

        if (authorInfo != null)
            joiner.add("**" + authorInfo.getName() + "**");

        if (title != null)
            joiner.add(title);

        if (description != null)
            joiner.add(description);

        for (MessageEmbed.Field field : messageEmbed.getFields())
            joiner.add(field.getName() + "\n" + field.getValue());

        if (footer != null)
            joiner.add(footer.getText());

        if (image != null) {
            String url = image.getUrl();
            String proxyUrl = image.getProxyUrl();

            if (url != null)
                joiner.add(url);

            else if (proxyUrl != null)
                joiner.add(proxyUrl);
        }

        if (thumbnail != null) {
            String url = thumbnail.getUrl();
            String proxyUrl = thumbnail.getProxyUrl();

            if (url != null)
                joiner.add(url);

            else if (proxyUrl != null)
                joiner.add(proxyUrl);
        }

        if (joiner.length() == 0)
            throw new IllegalStateException("When converting MessageEmbed to a String, produced an empty message.");

        return new MessageBuilder(joiner.toString()).build();
    }

    @Override
    public Message buildEmbed(ActionEvent<?, Message> event, MessageEmbed output) {
        return new MessageBuilder(output).build();
    }
}
