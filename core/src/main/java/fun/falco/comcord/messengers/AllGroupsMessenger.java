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

import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.utils.MarkdownUtil;
import fun.falco.comcord.api.DiscordMessenger;
import org.elypia.commandler.annotation.stereotypes.MessageProvider;
import org.elypia.commandler.event.ActionEvent;
import org.elypia.commandler.models.*;

import java.util.*;

@MessageProvider(provides = Message.class, value = AllGroupsModel.class)
public class AllGroupsMessenger implements DiscordMessenger<AllGroupsModel> {

    @Override
    public Message buildEmbed(ActionEvent<?, Message> event, AllGroupsModel groups) {
        EmbedBuilder builder = new EmbedBuilder();

        ControllerModel controller = groups.getHelpModel();
        builder.setTitle(controller.getName());
        builder.setDescription(controller.getDescription());

        for (CommandModel metaCommand : controller.getCommands()) {
            StringBuilder value = new StringBuilder();

            StringJoiner joiner = new StringJoiner("\n");

            for (PropertyModel property : metaCommand.getProperties())
                joiner.add(MarkdownUtil.bold(property.getDisplayName()) + ": " + MarkdownUtil.monospace(property.getValue()));

            joiner.add(metaCommand.getDescription());

            value.append("\n").append(joiner.toString());

            List<ParamModel> metaParams = metaCommand.getParams();

            if (!metaParams.isEmpty()) {
                value.append("\n" + "**Parameters**");

                metaParams.forEach((param) -> {
                    value.append("\n`").append(param.getName()).append("`: ");
                    value.append(param.getDescription());
                });
            }

            builder.addField(metaCommand.getName(), value.toString(), false);
        }

        builder.addField("Groups", String.join("\n", groups.getGroups().keySet()), false);
        return new MessageBuilder(builder).build();
    }

    @Override
    public Message buildMessage(ActionEvent<?, Message> event, AllGroupsModel groups) {
        StringBuilder builder = new StringBuilder("Groups");

        for (String group : groups.getGroups().keySet())
            builder.append("\n* ").append(group);

        return new MessageBuilder(builder).build();
    }
}
