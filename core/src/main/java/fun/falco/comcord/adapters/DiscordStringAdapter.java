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

package fun.falco.comcord.adapters;

import org.elypia.commandler.annotation.stereotypes.ParamAdapter;
import org.elypia.commandler.api.Adapter;
import org.elypia.commandler.event.ActionEvent;
import org.elypia.commandler.metadata.MetaParam;

import fun.falco.comcord.EventUtils;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.GenericEvent;

@ParamAdapter(CharSequence.class)
public class DiscordStringAdapter implements Adapter<CharSequence> {

    @Override
    public CharSequence adapt(String input, Class<? extends CharSequence> type, MetaParam metaParam, ActionEvent<?, ?> event) {
        if (!input.startsWith("msg:")) {
            return input;
        }

        String inputId = input.replace("msg:", "");
        long messageId = Long.parseLong(inputId);

        Object source = event.getRequest().getSource();

        if (!(source instanceof GenericEvent)) {
            return null;
        }

        GenericEvent sourceEvent = (GenericEvent) source;
        MessageChannel channel = EventUtils.getMessageChannel(sourceEvent);

        if (channel == null) {
            return null;
        }

        return channel.retrieveMessageById(messageId).complete().getContentRaw();
    }
}
