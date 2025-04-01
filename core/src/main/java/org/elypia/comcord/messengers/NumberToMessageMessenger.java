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

package org.elypia.comcord.messengers;

import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Message;
import org.elypia.comcord.api.DiscordMessenger;
import org.elypia.commandler.annotation.stereotypes.MessageProvider;
import org.elypia.commandler.event.ActionEvent;

import javax.inject.Inject;
import java.text.NumberFormat;

/**
 * @author seth@falco.fun (Seth Falco)
 */
@MessageProvider(provides = Message.class, value = {Number.class, Double.class, Float.class, Long.class, Integer.class, Short.class, Byte.class})
public class NumberToMessageMessenger implements DiscordMessenger<Number> {

    private NumberFormat format;

    public NumberToMessageMessenger() {
        this(NumberFormat.getInstance());
    }

    @Inject
    public NumberToMessageMessenger(NumberFormat format) {
        this.format = format;
    }

    @Override
    public Message buildMessage(ActionEvent<?, Message> event, Number output) {
        String formatted = event.getMetaCommand().getProperty(this.getClass(), "formatted").getValue();

        if (formatted.equalsIgnoreCase("true"))
            return new MessageBuilder(format.format(output)).build();
        else
            return new MessageBuilder(String.valueOf(output)).build();
    }
}
