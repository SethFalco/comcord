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

import java.util.*;

@MessageProvider(provides = Message.class, value = ControllerModel.class)
public class ControllerMessenger implements DiscordMessenger<ControllerModel> {

    @Override
    public Message buildEmbed(ActionEvent<?, Message> event, ControllerModel controller) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle(controller.getName());
        builder.setDescription(controller.getDescription());

        for (CommandModel metaCommand : controller.getCommands()) {
            StringBuilder value = new StringBuilder();
            value.append("\n" + metaCommand.getDescription());

            List<ParamModel> metaParams = metaCommand.getParams();

            if (!metaParams.isEmpty()) {
                value.append("\n" + "**Parameters**");

                metaParams.forEach((param) -> {
                    value.append("\n`" + param.getName() + "`: ");
                    value.append(param.getDescription());
                });
            }

            builder.addField(metaCommand.getName(), value.toString(), false);
        }

        builder.setFooter("Perform a command with: `{module alias} {command alias} {parameters}`");
        return new MessageBuilder(builder).build();
    }

    @Override
    public Message buildMessage(ActionEvent<?, Message> event, ControllerModel controller) {
        StringBuilder builder = new StringBuilder(controller.getName())
            .append("\n")
            .append(controller.getDescription());

        builder.append("\n\n");

        Iterator<CommandModel> commands = controller.getCommands().iterator();

        while (commands.hasNext()) {
            CommandModel metaCommand = commands.next();
            builder.append(metaCommand.getName())
                .append("\n")
                .append(metaCommand.getDescription());

            List<ParamModel> params = metaCommand.getParams();

            params.forEach((param) -> {
                builder.append("\n" + param.getName() + ": ");
                builder.append(param.getDescription());
            });

            if (commands.hasNext())
                builder.append("\n\n");
        }

        return new MessageBuilder(builder).build();
    }
}
