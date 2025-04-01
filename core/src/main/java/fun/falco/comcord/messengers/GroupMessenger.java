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

import org.elypia.commandler.annotation.stereotypes.MessageProvider;
import org.elypia.commandler.event.ActionEvent;
import org.elypia.commandler.models.ControllerModel;
import org.elypia.commandler.models.GroupModel;
import org.elypia.commandler.models.PropertyModel;

import fun.falco.comcord.api.DiscordMessenger;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.utils.MarkdownUtil;

@MessageProvider(provides = Message.class, value = GroupModel.class)
public class GroupMessenger implements DiscordMessenger<GroupModel> {

    @Override
    public Message buildMessage(ActionEvent<?, Message> event, GroupModel group) {
        StringBuilder builder = new StringBuilder();

        for (ControllerModel controller : group) {
            builder.append(controller.getName()).append("\n");

            for (PropertyModel property : controller.getProperties()) {
                builder.append(property.getDisplayName()).append(": ").append(property.getValue()).append("\n");
            }

            builder.append(controller.getDescription());
            builder.append("\n\n");
        }

        return new MessageBuilder(builder).build();
    }

    @Override
    public Message buildEmbed(ActionEvent<?, Message> event, GroupModel group) {
        EmbedBuilder builder = new EmbedBuilder();

        for (ControllerModel controller : group) {
            StringJoiner joiner = new StringJoiner("\n");

            for (PropertyModel property : controller.getProperties()) {
                joiner.add(MarkdownUtil.bold(property.getDisplayName()) + ": " + MarkdownUtil.monospace(property.getValue()));
            }

            joiner.add(controller.getDescription());
            builder.addField(controller.getName(), joiner.toString(), false);
        }

        return new MessageBuilder(builder).build();
    }
}
