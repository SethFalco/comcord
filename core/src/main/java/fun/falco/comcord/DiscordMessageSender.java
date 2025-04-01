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

package fun.falco.comcord;

import org.elypia.commandler.api.Integration;
import org.elypia.commandler.event.ActionEvent;
import org.elypia.commandler.managers.MessengerManager;
import org.elypia.commandler.producers.MessageSender;

import javax.enterprise.inject.Specializes;

@Specializes
public class DiscordMessageSender extends MessageSender {

    public DiscordMessageSender(Integration integration, MessengerManager messengerManager, ActionEvent event) {
        super(integration, messengerManager, event);
    }

    @Override
    public <T> void send(T object) {
        Object message = messengerManager.provide(event, object);
        integration.send(event, message);
    }
}
