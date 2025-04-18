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

import org.elypia.commandler.annotation.stereotypes.MessageProvider;
import org.elypia.commandler.event.ActionEvent;

import fun.falco.comcord.api.DiscordMessenger;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.internal.entities.DataMessage;

/**
 * @author seth@falco.fun (Seth Falco)
 */
@MessageProvider(provides = Message.class, value = {Message.class, DataMessage.class})
public class MessageMessenger implements DiscordMessenger<Message> {

    @Override
    public Message buildMessage(ActionEvent<?, Message> event, Message output) {
        return output;
    }
}
