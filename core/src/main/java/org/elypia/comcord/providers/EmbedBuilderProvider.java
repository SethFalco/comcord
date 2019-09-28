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

package org.elypia.comcord.providers;

import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.GenericMessageEvent;
import org.elypia.comcord.interfaces.DiscordProvider;
import org.elypia.commandler.CommandlerEvent;
import org.elypia.commandler.annotations.Provider;

/**
 * @author seth@elypia.org (Syed Shah)
 */
@Provider(provides = Message.class, value = EmbedBuilder.class)
public class EmbedBuilderProvider implements DiscordProvider<EmbedBuilder> {

    @Override
    public Message buildMessage(CommandlerEvent<?, ?> event, EmbedBuilder output) {
        GenericMessageEvent source = (GenericMessageEvent)event.getSource();

        if (source.getChannelType().isGuild())
            output.setColor(source.getGuild().getSelfMember().getColor());

        return new MessageBuilder(output.build()).build();
    }
}
