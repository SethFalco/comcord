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

package org.elypia.comcord.interfaces;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.GenericMessageEvent;
import org.elypia.commandler.CommandlerEvent;
import org.elypia.commandler.interfaces.ResponseProvider;

import java.util.Objects;

/**
 * @author seth@elypia.org (Syed Shah)
 */
public interface DiscordProvider<O> extends ResponseProvider<O, Message> {

    @Override
    default Message provide(CommandlerEvent<?, Message> event, O output) {
        Objects.requireNonNull(output);
        GenericMessageEvent source = (GenericMessageEvent)event.getSource();

        if (canSendEmbed(source)) {
            Message embed =  buildEmbed(event, output);

            if (embed != null)
                return embed;
        }

        return buildMessage(event, output);
    }

    Message buildMessage(CommandlerEvent<?, ?> event, O output);

    default Message buildEmbed(CommandlerEvent<?, ?> event, O output) {
        return null;
    }

    private boolean canSendEmbed(GenericMessageEvent source) {
        if (!source.getChannelType().isGuild())
            return true;

        Member self = source.getGuild().getSelfMember();
        TextChannel channel = source.getTextChannel();
        return self.hasPermission(channel, Permission.MESSAGE_EMBED_LINKS);
    }
}
