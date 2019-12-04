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

package org.elypia.comcord.api;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.GenericMessageEvent;
import org.elypia.commandler.api.Messenger;
import org.elypia.commandler.event.ActionEvent;

import java.util.Objects;

/**
 * @author seth@elypia.org (Seth Falco)
 */
public interface DiscordMessenger<O> extends Messenger<O, Message> {

    @Override
    default Message provide(ActionEvent<?, Message> event, O output) {
        Objects.requireNonNull(output);

        if (event != null && canSendEmbed((GenericMessageEvent)event.getRequest().getSource())) {
            Message embed =  buildEmbed(event, output);

            if (embed != null)
                return embed;
        }

        return buildMessage(event, output);
    }

    Message buildMessage(ActionEvent<?, Message> event, O output);

    default Message buildEmbed(ActionEvent<?, Message> event, O output) {
        return null;
    }

    private boolean canSendEmbed(GenericMessageEvent source) {
        if (!source.isFromGuild())
            return true;

        Member self = source.getGuild().getSelfMember();
        TextChannel channel = source.getTextChannel();
        return self.hasPermission(channel, Permission.MESSAGE_EMBED_LINKS);
    }
}
