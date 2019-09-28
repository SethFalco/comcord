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

package org.elypia.comcord;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.*;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.elypia.commandler.managers.DispatcherManager;
import org.slf4j.*;

/**
 * @author seth@elypia.org (Syed Shah)
 */
public class DiscordListener extends ListenerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(DiscordListener.class);

    private final DispatcherManager dispatcher;
    private final org.elypia.comcord.DiscordIntegration controller;

    public DiscordListener(final DispatcherManager dispatcher, org.elypia.comcord.DiscordIntegration controller) {
        this.dispatcher = dispatcher;
        this.controller = controller;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot())
            return;

        String content = event.getMessage().getContentRaw();

        Message message = dispatcher.dispatch(controller, event, content);

        if (message != null)
            event.getChannel().sendMessage(message).queue();
    }

    @Override
    public void onMessageUpdate(MessageUpdateEvent event) {
        String content = event.getMessage().getContentRaw();

        if (event.getAuthor().isBot())
            return;

        Message updatedMessage = event.getMessage();

        event.getChannel().getHistoryAfter(updatedMessage.getIdLong(), 1).queue(history -> {
            if (history.isEmpty())
                event.getChannel().sendMessage(dispatcher.dispatch(controller, event, content)).queue();
            else {
                Message nextMessage = history.getRetrievedHistory().get(0);

                if (nextMessage.getAuthor().getIdLong() == event.getJDA().getSelfUser().getIdLong())
                    nextMessage.editMessage(dispatcher.dispatch(controller, event, content)).queue();
            }
        });
    }
}
