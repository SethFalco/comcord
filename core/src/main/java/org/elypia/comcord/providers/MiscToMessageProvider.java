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

import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Message;
import org.elypia.commandler.CommandlerEvent;
import org.elypia.commandler.annotations.Provider;
import org.elypia.commandler.interfaces.ResponseProvider;

import java.net.URL;

/**
 * @author seth@elypia.org (Syed Shah)
 */
@Provider(provides = Message.class, value = {String.class, Character.class, char.class, Boolean.class, boolean.class, URL.class})
public class MiscToMessageProvider implements ResponseProvider<Object, Message> {

    @Override
    public Message provide(CommandlerEvent<?, Message> event, Object output) {
        return new MessageBuilder(output.toString()).build();
    }
}
