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

package org.elypia.comcord.messengers;

import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.entities.Message;
import org.elypia.comcord.api.DiscordMessenger;
import org.elypia.commandler.annotation.stereotypes.MessageProvider;
import org.elypia.commandler.event.ActionEvent;
import org.elypia.commandler.models.*;

@MessageProvider(provides = Message.class, value = GroupModel.class)
public class GroupMessenger implements DiscordMessenger<GroupModel> {

    @Override
    public Message buildEmbed(ActionEvent<?, Message> event, GroupModel group) {
        EmbedBuilder builder = new EmbedBuilder();

        for (ControllerModel controller : group)
            builder.addField(controller.getName(), controller.getDescription(), false);

        builder.setFooter("For more help, do: `help commands {module name}`");
        return new MessageBuilder(builder).build();
    }

    @Override
    public Message buildMessage(ActionEvent<?, Message> event, GroupModel group) {
        StringBuilder builder = new StringBuilder();

        for (ControllerModel controller : group) {
            builder.append(controller.getName()).append("\n").append(controller.getDescription());
            builder.append("\n\n");
        }

        return new MessageBuilder(builder).build();
    }
}
